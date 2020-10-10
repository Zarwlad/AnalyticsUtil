package ru.zarwlad.utrace.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfBriefedBusinessEventDto;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PostJobTwinService implements Runnable{
    static Logger log = LoggerFactory.getLogger(PostJobTwinService.class);
    private String location;

    public PostJobTwinService(String location) {
        this.location = location;
    }

    public PostJobTwinService() {
    }

    @Override
    public void run() {
        try {
            PostJobProcess(this.location);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void PostJobProcess(String location) throws IOException, InterruptedException {
        List<String> evFilter = new ArrayList<>();
        evFilter.add("&size=600");
        evFilter.add("&sort=created,asc");
        //evFilter.add("&priority=0");
        evFilter.add("&status=FILLED");
        evFilter.add("&location.id=" + location);
        //evFilter.add("&group=AGGREGATION");

        StringBuilder filtersForLog = new StringBuilder();
        evFilter.forEach(filtersForLog::append);
        log.info("Извлекаю данные по фильтрам: {}", filtersForLog.toString());
        PageDtoOfBriefedBusinessEventDto events = UtraceClient.getPagedEventsByFilter(evFilter);
        log.info("Извлечено событий: {}", events.getPage().getSize());

        List<EventDtoStatusProcessing> processings = new ArrayList<>();
        for (EventDto event : events.getData()) {
            Duration duration = Duration.between(DateTimeUtil.currentDateTime, LocalDateTime.now());
            if (duration.getSeconds() > 12600L){
                break;
            }

            EventDtoStatusProcessing processing = new EventDtoStatusProcessing();
            processing.setEventDto(event);
            processing.setStartProcessing(LocalDateTime.now());

            log.info("Cобытие {}, startProcessing {}", event.getId(), processing.getStartProcessing());
            String s = UtraceClient.postEventByIdReturnString(event.getId());
            log.info("Ответ по событию {}: {}", event.getId(), s);
            processing.setEndProcessing(LocalDateTime.now());
            log.info("Событие {}, endProcessing {}", event.getId(), processing.getEndProcessing());

            processing.setDuration(Duration.between(processing.getStartProcessing(), processing.getEndProcessing()));
            log.info("Событие {}, duration {}", event.getId(), processing.getDuration().getSeconds());

            if (s.contains("504")) {
                processing.setStatus(ProcStatus.TIME_OVER);
                log.info("Событие {}, status {}", event.getId(), processing.getStatus());

                processings.add(processing);

                log.info("Событие {}, засыпаю на {}", event.getId(), 300_000);
                Thread.sleep(300_000);
            } else if (s.contains("{}")) {
                processing.setStatus(ProcStatus.SUCCESS);
                log.info("Событие {}, status {}", event.getId(), processing.getStatus());
            } else {
                processing.setStatus(ProcStatus.FAILED);
                log.info("Событие {}, status {}", event.getId(), processing.getStatus());
                processing.setErrorText(s);
            }
            processings.add(processing);
        }

        for (EventDtoStatusProcessing processing : processings) {
            if (ProcStatus.TIME_OVER.equals(processing.getStatus())) {
                EventDto eventDto = UtraceClient.getEventById(processing.getEventDto().getId());
                if (eventDto.getStatus().equals("POSTED")) {
                    processing.setStatus(ProcStatus.SUCCESS);
                } else if (eventDto.getStatus().equals("WAITING")) {
                    processing.setStatus(ProcStatus.ERROR_TIME_OVER);
                }
            }
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class EventDtoStatusProcessing {
        EventDto eventDto;
        ProcStatus status;
        LocalDateTime startProcessing;
        LocalDateTime endProcessing;
        Duration duration;
        String errorText;
    }

    enum ProcStatus{
        SUCCESS,
        FAILED,
        TIME_OVER,
        ERROR_TIME_OVER
    }
}

