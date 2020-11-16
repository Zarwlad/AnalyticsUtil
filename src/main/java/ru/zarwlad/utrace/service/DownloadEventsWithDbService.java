package ru.zarwlad.utrace.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventDto;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfBriefedBusinessEventDto;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DownloadEventsWithDbService {
    private static Logger log = LoggerFactory.getLogger(DownloadEventsWithDbService.class);

    static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static void downloadEventsByFileIds(){
        Path fileWithIds = Paths.get(properties.getProperty("fileEventIds"));
        List<String> lines = null;
        List<String> n = null;

        EventDao eventDao = new EventDao(DbManager.getSessionFactory());
        List<String> uploaded = eventDao.readAllIdsFromEventsByClient().stream()
                .map(x -> x.getId().toString()).collect(Collectors.toList());

        try {
            lines = Files.readAllLines(fileWithIds);
            n = lines.stream()
                    .map(x -> x.replaceAll("\"", ""))
                    .filter(line -> !uploaded.contains(line))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        downloadByList(n);
    }

    public static void downloadEventsByPageEventDto(List<EventDto> eventDtos){
        downloadByList(eventDtos
                .stream()
                .map(EventDto::getId)
                .collect(Collectors.toList())
        );
    }

    private static void downloadByList (List<String> lines) {
        List<List<String>> batchedIds = new ArrayList<>();

        List<String> portion = new ArrayList<>();

        assert lines != null;
        int threadBatchDivider = lines.size() / 6;

        if (threadBatchDivider == 0)
            threadBatchDivider = 1;

        for (int i = 0; i < lines.size(); i++) {
            portion.add(lines.get(i));
            if (portion.size() == threadBatchDivider){
                batchedIds.add(portion);
                portion = new ArrayList<>();
            }
            if (i == lines.size()-1 &&
                    portion.size() % threadBatchDivider != 0){
                batchedIds.add(portion);
            }
        }

        for (List<String> batchedId : batchedIds) {
            MultiThreadEventDownloader downloader = new MultiThreadEventDownloader();
            downloader.setIds(batchedId);

            Thread thread = new Thread(downloader);
            thread.start();
        }

    }

    public static void downloadMissedEventStatuses(){
        EventDao eventDao = new EventDao(DbManager.getSessionFactory());
        List<Event> events = eventDao.readAll();

        Path fileDownloadedIds = Paths.get(properties.getProperty("fileEventIds"));
        List<String> ids = new ArrayList<>();
        try {
            ids = Files.readAllLines(fileDownloadedIds);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Event> purifiedEvents = new ArrayList<>();

        for (Event event : events) {
            if (!ids.contains(event.getId().toString()))
                purifiedEvents.add(event);
        }

        events = purifiedEvents;

        List<List<EventDto>> eventsDtoLists = new ArrayList<>();

        List<EventDto> portion = new ArrayList<>();

        int threadBatchDivider = events.size() / 6;

        for (int i = 0; i < events.size(); i++) {
            EventDto eventDto = new EventDto();
            eventDto.setId(events.get(i).getId().toString());
            eventDto.setType(events.get(i).getType());

            portion.add(eventDto);
            if (portion.size() == threadBatchDivider){
                eventsDtoLists.add(portion);
                portion = new ArrayList<>();
            }
            if (i == events.size()-1 &&
                    portion.size() % threadBatchDivider != 0){
                eventsDtoLists.add(portion);
            }
        }
        for (List<EventDto> batchedId : eventsDtoLists) {
            MultiThreadEventDownloader downloader = new MultiThreadEventDownloader();
            downloader.setEventDtos(batchedId);

            Thread thread = new Thread(downloader);
            thread.start();
        }
    }

}
