package ru.zarwlad.utrace.data;

import ru.zarwlad.utrace.model.EventStatistic;

import java.util.HashSet;
import java.util.Set;

public class EventStatsData {
    private Set<EventStatistic> eventStatistics = new HashSet<>();
    private static EventStatsData instance;

    private EventStatsData(){}

    public static EventStatsData getInstance() {
        if (instance == null){
            instance = new EventStatsData();
        }
        return instance;
    }

    public Set<EventStatistic> getEventStatistics() {
        return eventStatistics;
    }

    public void setEventStatistics(Set<EventStatistic> eventStatistics) {
        this.eventStatistics = eventStatistics;
    }
}
