package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.data.EventData;
import ru.zarwlad.utrace.data.EventStatsData;
import ru.zarwlad.utrace.model.AverageCount;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStat;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class EventStatisticCounterService {
    private static Logger log = LoggerFactory.getLogger(EventStatisticCounterService.class);

    public static void calculateStatistic(){
        EventData eventData = EventData.getInstance();
        EventStatsData eventStatsData = EventStatsData.getInstance();

        Set<EventStat> eventStats = new HashSet<>();

        for (Event event : eventData.getEvents()) {
            log.info("Рассчитываю статистику по событию id = {}", event.getId());
            eventStats.add(event.fromEventToEventStat());
        }
        eventStatsData.setEventStatistics(eventStats);
    }

    public static AverageCount calculateAverageForMonth(){
        EventStatsData eventStatsData = EventStatsData.getInstance();
        BigDecimal totalSendingForAllEvents = BigDecimal.valueOf(0);
        int totalEventsSended = 0;

        Properties properties = new Properties();
        Month targetMonth = null;

        try {
            properties.load(new FileReader("src\\main\\resources\\application.properties"));
            targetMonth = Month.valueOf(properties.getProperty("targetMonth"));
        }
        catch (Exception e){
            log.error(e.toString());
        }

        for (EventStat eventStat : eventStatsData.getEventStatistics()) {
            if (eventStat.getEventMonth().equals(targetMonth)){
                if (eventStat.getIsMessageCreated()){
                    totalSendingForAllEvents = totalSendingForAllEvents.add(eventStat.getTotalSendingSeconds());
                    totalEventsSended++;
                }
            }
        }

        BigDecimal average = null;
        if (totalEventsSended != 0)
            average = totalSendingForAllEvents.divide(BigDecimal.valueOf(totalEventsSended), 2, RoundingMode.HALF_EVEN);

        return new AverageCount(totalSendingForAllEvents, totalEventsSended, average);
    }
}