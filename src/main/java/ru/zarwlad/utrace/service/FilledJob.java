package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.Main;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfBriefedBusinessEventDto;
import ru.zarwlad.utrace.util.client.PropertiesConfig;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilledJob implements Runnable{
    private static Logger log = LoggerFactory.getLogger(FilledJob.class);

    @Override
    public void run() {
        validateEvents();
    }

    private static void validateEvents(){
        List<String> filter = new ArrayList<>(Arrays.asList(PropertiesConfig.properties.getProperty("kraken.evFilterValidation").split(";")));
        try {
            log.info("Thread {}: запускаю валидацию для событий", Thread.currentThread());
            PageDtoOfBriefedBusinessEventDto eventDto = UtraceClient.getPagedEventsByFilter(filter);
            eventDto.getData().forEach(x -> {
                try {
                    log.info("Thread {}, валидирую событие {}", Thread.currentThread(), x.getId());
                    UtraceClient.validateEventById(x.getId());
                } catch (IOException e) {
                   log.error(e.getLocalizedMessage());
                }
            });
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

}
