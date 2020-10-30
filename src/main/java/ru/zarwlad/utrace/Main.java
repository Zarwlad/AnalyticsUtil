package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfBriefedBusinessEventDto;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.util.client.PropertiesConfig;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.FileReader;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        log.info("Начинаю скачивать события");
        List<String> f = new ArrayList<>();
        f.add("&size=2000");
        f.add("&sort=created,desc");

        Properties properties = new Properties();
        Month targetMonth = null;

        try {
            properties.load(new FileReader("src\\main\\resources\\application.properties"));
            targetMonth = Month.valueOf(properties.getProperty("targetMonth"));
        }
        catch (Exception e){
            log.error(e.toString());
        }

        PageDtoOfBriefedBusinessEventDto eventsDto = UtraceClient.getPagedEventsByFilter(f);
        Month finalTargetMonth = targetMonth;

        DownloadEventsWithDbService.downloadEventsByPageEventDto(
                eventsDto.getData().stream()
                .filter(x -> DateTimeUtil
                        .toZonedDateTime(x.getCreated())
                        .getMonth()
                        .equals(finalTargetMonth))
                .collect(Collectors.toList()));

        //DownloadEventsWithDbService.downloadEventsByFileIds();

        log.info("Рассчитываю статистикику по событиям");
        MultiThreadStartCalculateDbEvents calculateDbEvents = new MultiThreadStartCalculateDbEvents();
        Thread thread = new Thread(calculateDbEvents);
        thread.start();
    }

}
