package ru.zarwlad.utrace.data;

import ru.zarwlad.utrace.model.EventStat;

import java.util.HashSet;
import java.util.Set;

public class EventStatsData {
    private Set<EventStat> eventStats = new HashSet<>();
    private static EventStatsData instance;

    private EventStatsData(){}

    public static EventStatsData getInstance() {
        if (instance == null){
            instance = new EventStatsData();
        }
        return instance;
    }

    public Set<EventStat> getEventStatistics() {
        return eventStats;
    }

    public void setEventStatistics(Set<EventStat> eventStats) {
        this.eventStats = eventStats;
    }
}
