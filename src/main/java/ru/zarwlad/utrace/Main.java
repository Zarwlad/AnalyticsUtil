package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.*;

import java.io.IOException;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        log.info("Начинаю скачивать события");
        DownloadEventsWithDbService.downloadEventsByIds();

        log.info("Рассчитываю статистикику по событиям");
        MultiThreadStartCalculateDbEvents calculateDbEvents = new MultiThreadStartCalculateDbEvents();
        Thread thread = new Thread(calculateDbEvents);
        thread.start();

    }

}
