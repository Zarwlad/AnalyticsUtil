package utrace.service;

import utrace.data.EventData;
import utrace.data.EventStatsData;
import utrace.entities.Event;
import utrace.entities.EventStatistic;

import java.util.HashSet;
import java.util.Set;

public class EventStatisticCounterService {
    public static void calculateStatistic(){
        EventData eventData = EventData.getInstance();
        EventStatsData eventStatsData = EventStatsData.getInstance();

        Set<EventStatistic> eventStatistics = new HashSet<>();

        for (Event event : eventData.getEvents()) {
            eventStatistics.add(event.fromEventToEventStat());
        }

        eventStatsData.setEventStatistics(eventStatistics);

    }
}