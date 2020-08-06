package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.dao.EventStatDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatistic;
import ru.zarwlad.utrace.model.EventStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventStatsDbCounter {
    private static Logger log = LoggerFactory.getLogger(EventStatisticCounterService.class);

    public static void calculateStats(){
        EventDao eventDao = new EventDao(DbManager.getSessionFactory());
        EventStatDao eventStatDao = new EventStatDao(DbManager.getSessionFactory());
        List<Event> events = eventDao.read500NotCalculatedEvents();

        for (Event event : events) {
            EventStatistic eventStatistic = new EventStatistic();
            eventStatistic.setEvent(event);

//            eventStatistic.setEventPostingSeconds();
//            eventStatistic.setEventPostingSeconds();
//            eventStatistic.setMessagesSendSecondsAvg();
//            eventStatistic.setTotalSendingSeconds();
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

        Duration duration = Duration.between(minValue.getChangeOperationDate(),
                maxValue.getChangeOperationDate());

        return BigDecimal.valueOf(duration.toMillis());
    }

}
