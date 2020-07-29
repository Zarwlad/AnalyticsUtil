package ru.zarwlad.utrace.model;

import ru.zarwlad.unitedDtos.utraceDto.Dto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Event implements Entity{
    private String id;
    private String type;
    private ZonedDateTime operationDate;
    private String status;
    private String regulatorStatus;
    private Set<EventStatus> eventStatuses;
    private Set<Message> messages;
    private ZonedDateTime createdDate;

    public Event() {
    }

    public Event(String id,
                 String type,
                 ZonedDateTime operationDate,
                 String status,
                 String regulatorStatus,
                 Set<EventStatus> eventStatuses,
                 Set<Message> messages,
                 ZonedDateTime createdDate) {
        this.id = id;
        this.type = type;
        this.operationDate = operationDate;
        this.status = status;
        this.regulatorStatus = regulatorStatus;
        this.eventStatuses = eventStatuses;
        this.messages = messages;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(ZonedDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public Set<EventStatus> getEventStatuses() {
        return eventStatuses;
    }

    public void setEventStatuses(Set<EventStatus> eventStatuses) {
        this.eventStatuses = eventStatuses;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegulatorStatus() {
        return regulatorStatus;
    }

    public void setRegulatorStatus(String regulatorStatus) {
        this.regulatorStatus = regulatorStatus;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return getId().equals(event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return new EventDto();
    }

    public EventStatistic fromEventToEventStat(){
        BigDecimal eventPostingSeconds = null;
        BigDecimal messagesSendSecondsAvg = null;
        Boolean isErrorEvent = null;
        MessageErrorEnum isErrorMessage;
        Boolean isEventPosted;
        Boolean isMessageCreated;
        Month eventMonth;
        BigDecimal totalSendingSeconds = null;

        if (this.getStatus().equals("POSTED")) {
            isEventPosted = true;
            isErrorEvent = false;
        }
        else {
            isEventPosted = false;

            if (this.getStatus().equals("ERROR"))
                isErrorEvent = true;
            else
                isErrorEvent = false;
        }

        try {
            if (!this.getMessages().isEmpty()) {
                isMessageCreated = true;
                if (this.getRegulatorStatus().equals("ERROR"))
                    isErrorMessage = MessageErrorEnum.YES;
                else
                    isErrorMessage = MessageErrorEnum.NO;
            } else {
                isMessageCreated = false;
                isErrorMessage = MessageErrorEnum.NOT_SEND;
            }
        }
        catch (NullPointerException e){
            isMessageCreated = false;
            isErrorMessage = MessageErrorEnum.NOT_SEND;
        }

        /*
        Время разноски события вычисляется как разница между датой присвоения первого статуса Filled и дата присвоения
        статуса Posted.
        В EventStatus попадают значения, отфильтрованные в DownloadEventsService - только те записи, у которых есть
        параметр изменения - статус.
         */

        ZonedDateTime firstFilledStatusDate;
        ZonedDateTime postedStatusDate = null;

        if (isEventPosted){
            ZonedDateTime tempFilledStatusDate = null;

            for (EventStatus eventStatus : this.getEventStatuses()) {
                if (eventStatus.getStatus().equals("FILLED")){
                    if (tempFilledStatusDate == null ||
                            eventStatus.getChangeOperationDate().isBefore(tempFilledStatusDate)){
                        tempFilledStatusDate = eventStatus.getChangeOperationDate();
                    }
                }

                if (eventStatus.getStatus().equals("POSTED")){
                    postedStatusDate = eventStatus.getChangeOperationDate();
                }
            }

            firstFilledStatusDate = tempFilledStatusDate;

            if (firstFilledStatusDate != null && postedStatusDate != null) {
                Duration postingTime = Duration.between(firstFilledStatusDate, postedStatusDate);
                eventPostingSeconds = BigDecimal.valueOf(postingTime.getSeconds());
            }

            /*
                Вычисляем период отправки в МДЛП как разницу между первым созданным сообщением и датой отправки
                последнего сообщения.
                Use-cases:
                1. Одно событие - одно сообщение.
                2. Одно событие - несколько сообщений, созданных в одну итерацию (разделены по числу кодов внутри).
                3. Одно событие - несколько сообщений, созданных в нескольких итераций (переотправка ошибочных
                сообщений).

                Для каждого сообщения будет рассчитан период отправки
             */

            //TODO: покрыть случай, когда сообщение создано, но не отпправлено

            if (isMessageCreated){
                List<Duration> messagesSendDur = new ArrayList<>();

                for (Message message : this.getMessages()) {
                    ZonedDateTime createdDate = null;
                    ZonedDateTime sendDate = null;

                    for (MessageHistory messageHistory : message.getMessageHistories()) {
                        if (messageHistory.getStatus().equals("CREATED")) {
                            if (createdDate == null) {
                                createdDate = messageHistory.getCreated();
                            }
                            else if (createdDate.isAfter(messageHistory.getCreated())){
                                createdDate = messageHistory.getCreated();
                            }
                        }

                        if (messageHistory.getStatus().equals("SENT")) {
                            if (sendDate == null) {
                                sendDate = messageHistory.getCreated();
                            }
                            else if (sendDate.isAfter(messageHistory.getCreated())){
                                sendDate = messageHistory.getCreated();
                            }
                        }
                    }
                    if (createdDate != null && sendDate != null) {
                        Duration messagesSendingDur = Duration.between(createdDate, sendDate);
                        messagesSendDur.add(messagesSendingDur);
                    }
                }

                if (!messagesSendDur.isEmpty()){
                    BigDecimal totalTimeSending = null;

                    for (Duration duration : messagesSendDur) {
                        if (totalTimeSending == null){
                            totalTimeSending = BigDecimal.valueOf(duration.getSeconds());
                        }
                        else {
                            try {
                                totalTimeSending = totalTimeSending.add(BigDecimal.valueOf(duration.getSeconds()));
                            }
                            catch (NullPointerException e){
                                System.out.println("Ошибка расчета суммарного времени отправки по событию: " + this.getId()
                                + "\ntotalTimeSpending: " + totalTimeSending);
                                for (Message message : messages) {
                                    System.out.println("messageId: " + message.getId());
                                }
                                for (Duration duration1 : messagesSendDur) {
                                    System.out.println("duration: " + duration1.getSeconds());
                                }
                            }
                        }
                    }
                    if (totalTimeSending != null) {
                        System.out.println("totalTimeSending: " + totalTimeSending);
                        messagesSendSecondsAvg = totalTimeSending.divide(
                                BigDecimal.valueOf(messagesSendDur.size()),
                                2,
                                RoundingMode.HALF_EVEN);
                    }
                }
            }
        }
        eventMonth = Month.from(this.getCreatedDate());

        if (eventPostingSeconds != null && messagesSendSecondsAvg != null){
            totalSendingSeconds = eventPostingSeconds.add(messagesSendSecondsAvg);
        }
        else if (eventPostingSeconds != null){
            totalSendingSeconds = eventPostingSeconds;
        }
        else if (messagesSendSecondsAvg != null){
            totalSendingSeconds = messagesSendSecondsAvg;
        }

        return  new EventStatistic(this,
                eventPostingSeconds,
                messagesSendSecondsAvg,
                isErrorEvent,
                isErrorMessage,
                isEventPosted,
                isMessageCreated,
                eventMonth,
                totalSendingSeconds);
    }

}
