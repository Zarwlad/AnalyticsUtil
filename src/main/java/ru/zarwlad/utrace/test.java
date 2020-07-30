package ru.zarwlad.utrace;

import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.dao.EventStatusDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.service.DbManager;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
        Event event = new Event();
        event.setType("123");
        event.setId(UUID.randomUUID());
        event.setStatus("123");
        event.setRegulatorStatus("erre");
        event.setCreatedDate(ZonedDateTime.now());
        event.setOperationDate(ZonedDateTime.now());
        event.setClient("2412");

        EventDao eventDao = new EventDao(DbManager.getSessionFactory());

        EventStatus es = new EventStatus();
        es.setEvent(event);
        es.setStatus("1231");
        es.setId(UUID.randomUUID());
        es.setChangeOperationDate(ZonedDateTime.now());

        Set<EventStatus> set = new HashSet<>();
        set.add(es);

        event.setEventStatuses(set);

        eventDao.create(event);
        for (EventStatus eventStatus : set) {
            EventStatusDao esd = new EventStatusDao(DbManager.getSessionFactory());
            esd.create(es);
        }
    }
}
