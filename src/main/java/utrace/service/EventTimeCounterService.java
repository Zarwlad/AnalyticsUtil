package utrace.service;

import utrace.data.EventData;
import utrace.entities.Event;

public class EventTimeCounterService {
    public static void showAllEvents(){
        for (Event event : EventData.getInstance().getEvents()) {
            System.out.println(event.getId() + " тип события: " + event.getType());
        }
    }
}
