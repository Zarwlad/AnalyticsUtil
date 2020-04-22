package utrace.entities;

import utrace.dto.Dto;

import java.math.BigDecimal;
import java.time.Month;


public class EventStatistic implements Entity {
    private Event event;
    private BigDecimal eventPostingSeconds;
    private BigDecimal messagesSendSecondsAvg;
    private Boolean isErrorEvent;
    private MessageErrorEnum isErrorMessage;
    private Boolean isEventPosted;
    private Boolean isMessageCreated;
    private Month eventMonth;
    private BigDecimal totalSendingSeconds;

    public EventStatistic() {
    }

    public EventStatistic(Event event,
                          BigDecimal eventPostingSeconds,
                          BigDecimal messagesSendSecondsAvg,
                          Boolean isErrorEvent,
                          MessageErrorEnum isErrorMessage,
                          Boolean isEventPosted,
                          Boolean isMessageCreated,
                          Month eventMonth,
                          BigDecimal totalSendingSeconds) {
        this.event = event;
        this.eventPostingSeconds = eventPostingSeconds;
        this.messagesSendSecondsAvg = messagesSendSecondsAvg;
        this.isErrorEvent = isErrorEvent;
        this.isErrorMessage = isErrorMessage;
        this.isEventPosted = isEventPosted;
        this.isMessageCreated = isMessageCreated;
        this.eventMonth = eventMonth;
        this.totalSendingSeconds = totalSendingSeconds;
    }

    public MessageErrorEnum getIsErrorMessage() {
        return isErrorMessage;
    }

    public void setIsErrorMessage(MessageErrorEnum isErrorMessage) {
        this.isErrorMessage = isErrorMessage;
    }

    public BigDecimal getTotalSendingSeconds() {
        return totalSendingSeconds;
    }

    public void setTotalSendingSeconds(BigDecimal totalSendingSeconds) {
        this.totalSendingSeconds = totalSendingSeconds;
    }

    public Boolean getEventPosted() {
        return isEventPosted;
    }

    public void setEventPosted(Boolean eventPosted) {
        isEventPosted = eventPosted;
    }

    public Boolean getMessageCreated() {
        return isMessageCreated;
    }

    public void setMessageCreated(Boolean messageCreated) {
        isMessageCreated = messageCreated;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public BigDecimal getEventPostingSeconds() {
        return eventPostingSeconds;
    }

    public void setEventPostingSeconds(BigDecimal eventPostingSeconds) {
        this.eventPostingSeconds = eventPostingSeconds;
    }

    public BigDecimal getMessagesSendSecondsAvg() {
        return messagesSendSecondsAvg;
    }

    public void setMessagesSendSecondsAvg(BigDecimal messagesSendSecondsAvg) {
        this.messagesSendSecondsAvg = messagesSendSecondsAvg;
    }

    public Boolean getErrorEvent() {
        return isErrorEvent;
    }

    public void setErrorEvent(Boolean errorEvent) {
        isErrorEvent = errorEvent;
    }

    public MessageErrorEnum getErrorMessage() {
        return isErrorMessage;
    }

    public void setErrorMessage(MessageErrorEnum errorMessage) {
        isErrorMessage = errorMessage;
    }

    public Month getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(Month eventMonth) {
        this.eventMonth = eventMonth;
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return null;
    }

}
