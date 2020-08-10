package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.dao.EventStatDao;
import ru.zarwlad.utrace.model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventStatsDbCounter {
    private static Logger log = LoggerFactory.getLogger(EventStatisticCounterService.class);


    public static void calculateStats(){
        EventDao eventDao = new EventDao(DbManager.getSessionFactory());
        EventStatDao eventStatDao = new EventStatDao(DbManager.getSessionFactory());

        List<Event> events = eventDao.read500NotCalculatedEvents();

        while (!events.isEmpty()) {
            for (Event event : events) {
                    EventStat eventStat = new EventStat();
                    eventStat.setEvent(event);
                    eventStat.setEventPostingSeconds(EventStatsDbCounter.calcEventStatusesStat(event));

                    if (!"QUEUE".equals(event.getRegulatorStatus())
                            && !"NOT_REQUIRED".equals(event.getRegulatorStatus())
                            && !"ARTIFICIAL".equals(event.getRegulatorStatus())) {
                        eventStat.setMessagesSendSecondsAvg(EventStatsDbCounter.calcAvgMsgSend(event));
                        eventStat.setTotalSendingSeconds(eventStat.getMessagesSendSecondsAvg()
                                .add(eventStat.getEventPostingSeconds()));
                    } else {
                        eventStat.setTotalSendingSeconds(eventStat.getEventPostingSeconds());
                    }
                    eventStatDao.create(eventStat);
                }
            events = eventDao.read500NotCalculatedEvents();
        }
    }

    private static BigDecimal calcEventStatusesStat(Event event){
        List<EventStatus> statuses = new ArrayList<>(event.getEventStatuses());

        Comparator<EventStatus> comparator =
                Comparator.comparing(EventStatus::getChangeOperationDate);
        statuses.sort(comparator);

        EventStatus minValue = null; //первое значение filled
        EventStatus maxValue = null; //значение проведено или последнее значение ожидание

        for (EventStatus status : statuses) {
            switch (status.getStatus()){
                case "FILLED":
                    if (minValue == null) {
                        minValue = status;
                        continue;
                    }
                    else {
                        if (minValue.getChangeOperationDate()
                                .isAfter(status.getChangeOperationDate()))
                            minValue = status;
                        continue;
                    }
                case "POSTED":
                    maxValue = status;
                    continue;

                case "WAITING":
                    if (maxValue != null){
                        if (!"POSTED".equals(maxValue.getStatus())){
                            if (maxValue.getChangeOperationDate().isBefore(status.getChangeOperationDate())){
                                maxValue = status;
                            }
                        }
                    }
                    else
                        maxValue = status;
            }
        }
        Duration duration = null;
        try {
            duration = Duration.between(minValue.getChangeOperationDate(),
                    maxValue.getChangeOperationDate());
        }
        catch (NullPointerException e){
            log.error(e.getLocalizedMessage());
            log.error("event " + event.getId());
        }
        return BigDecimal.valueOf(duration.toMillis());
    }

    private static BigDecimal calcAvgMsgSend(Event event){
        List<MessageHistory> histories = new ArrayList<>();
        for (Message message : event.getMessages()) {
            histories.addAll(message.getMessageHistories());
        }
        
        Comparator<MessageHistory> comparator = Comparator.comparing(MessageHistory::getCreated);
        histories.sort(comparator);
        
        MessageHistory minValue = null;
        MessageHistory maxValue = null;

        for (MessageHistory history : histories) {
            switch (history.getStatus()) {
                case "CREATED":
                    if (minValue == null
                    || minValue.getCreated().isAfter(history.getCreated())) {
                        minValue = history;
                        continue;
                    }
                case "SENT":
                    if (maxValue == null
                    || maxValue.getCreated().isBefore(history.getCreated()))
                        maxValue = history;
            }
        }

        if (!histories.contains("SENT")){
            for (MessageHistory history : histories) {
                if ("TECH_ERROR".equals(history.getStatus())
                || "ERROR".equals(history.getStatus())){
                    maxValue = history;
                    break;
                }
            }
        }

        try {
            Duration duration = Duration.between(minValue.getCreated(), maxValue.getCreated());
            return BigDecimal.valueOf(duration.toMillis());
        }
        catch (NullPointerException e){
            log.error(e.getLocalizedMessage());
            log.error(event.getId().toString());
            log.error("minValue " + minValue.toString());
            log.error("maxValue " + maxValue.toString());

            return null;
        }
    }

}
