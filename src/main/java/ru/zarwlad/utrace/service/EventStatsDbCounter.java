package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.dao.EventStatDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatistic;

import java.util.List;

public class EventStatsDbCounter {
    private static Logger log = LoggerFactory.getLogger(EventStatisticCounterService.class);

    public static void calculateStats(){
        EventDao eventDao = new EventDao(DbManager.getSessionFactory());

        List<Event> events = eventDao.readAll();

        EventStatDao eventStatDao = new EventStatDao(DbManager.getSessionFactory());
        List<EventStatistic> eventStatistics = eventStatDao.readAll();

        

    }

}
