package ru.zarwlad.utrace.data;

import ru.zarwlad.utrace.model.Event;

import java.util.HashSet;
import java.util.Set;

public class EventData {
    private Set<Event> events = new HashSet<>();

    private static EventData instance;

    private EventData(){
    }

    public static EventData getInstance() {
        if (instance == null){
            instance = new EventData();
        }
        return instance;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
