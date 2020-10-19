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
import ru.zarwlad.utrace.util.client.PropertiesConfig;
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
import java.util.Arrays;
import java.util.Collections;
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
        } catch (IOException | InterruptedException ignored) {
        }
    }

    public static void PostJobProcess(String location) throws IOException, InterruptedException {
        List<String> evFilter = new ArrayList<>(Arrays.asList(PropertiesConfig.properties.getProperty("kraken.evFilter").split(";")));
        evFilter.add("&location.id=" + location);


        StringBuilder filtersForLog = new StringBuilder();
        evFilter.forEach(filtersForLog::append);
        log.info("Thread {}, Извлекаю данные по фильтрам: {}", Thread.currentThread().getName(), filtersForLog.toString());
        PageDtoOfBriefedBusinessEventDto events = UtraceClient.getPagedEventsByFilter(evFilter);
        log.info("Thread {}, Извлечено событий: {}", Thread.currentThread().getName(), events.getPage().getSize());

        List<EventDto> registers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (EventDto event : events.getData()) {
            Duration duration = Duration.between(DateTimeUtil.currentDateTime, LocalDateTime.now());
            if (duration.getSeconds() > 12600L)
                break;

            EventDtoStatusProcessing processing = processEvent(event);
//            if (event.getType().equals("REGISTER")) {
//                registers.add(event);
//            } else {
//                EventDtoStatusProcessing processing = processEvent(event);
//            }
//
//            if (!registers.isEmpty() &&
//                            (registers.size() == 200
//                            || events.getData().indexOf(event) == events.getData().size() -1)){
//                MultiThreadEvents multiThreadEvents = new MultiThreadEvents();
//                multiThreadEvents.setEventDtos(new ArrayList<>(List.copyOf(registers)));
//                Thread thread = new Thread(multiThreadEvents);
//                threads.add(thread);
//                registers.clear();
//            }
//            threads.forEach(Thread::start);
//
//            threads.forEach(x -> {
//                try {
//                    x.join();
//                } catch (InterruptedException e) {
//                    log.error(e.getLocalizedMessage());
//                }
//            });

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


    private static EventDtoStatusProcessing processEvent(EventDto event) throws IOException, InterruptedException {
        EventDtoStatusProcessing processing = new EventDtoStatusProcessing();
        processing.setEventDto(event);
        processing.setStartProcessing(LocalDateTime.now());

        log.info("Thread {}, Cобытие {}, startProcessing {}", Thread.currentThread().getName(), event.getId(), processing.getStartProcessing());
        String s = UtraceClient.postEventByIdReturnString(event.getId());

        log.info("Thread {}, Ответ по событию {}: {}", Thread.currentThread().getName(), event.getId(), s);
        processing.setEndProcessing(LocalDateTime.now());
        log.info("Thread {}, Событие {}, endProcessing {}", Thread.currentThread().getName(), event.getId(), processing.getEndProcessing());

        processing.setDuration(Duration.between(processing.getStartProcessing(), processing.getEndProcessing()));
        log.info("Thread {}, Событие {}, duration {}", Thread.currentThread().getName(), event.getId(), processing.getDuration().getSeconds());

        if (s.contains("504")) {
            processing.setStatus(ProcStatus.TIME_OVER);

            log.info("Thread {}, Событие {}, status {}", Thread.currentThread().getName(), event.getId(), processing.getStatus());

            log.info("Thread {}, Событие {}, засыпаю на {}", Thread.currentThread().getName(), event.getId(), Long.parseLong(PropertiesConfig.properties.getProperty("kraken.sleepMs")));

            Thread.sleep(Long.parseLong(PropertiesConfig.properties.getProperty("kraken.sleepMs")));

        } else if (s.contains("{}")) {
            processing.setStatus(ProcStatus.SUCCESS);
            log.info("Thread {}, Событие {}, status {}", Thread.currentThread().getName(), event.getId(), processing.getStatus());
        } else {
            processing.setStatus(ProcStatus.FAILED);
            log.info("Thread {}, Событие {}, status {}", Thread.currentThread().getName(), event.getId(), processing.getStatus());
            processing.setErrorText(s);
        }

        return processing;
    }


    private static class MultiThreadEvents implements Runnable{
        private List<EventDto> eventDtos;

        public List<EventDto> getEventDtos() {
            return eventDtos;
        }

        public void setEventDtos(List<EventDto> eventDtos) {
            this.eventDtos = eventDtos;
        }

        @Override
        public void run() {
            eventDtos.forEach(x -> {
                try {
                    PostJobTwinService.processEvent(x);
                } catch (IOException | InterruptedException ignored) {
                }
            });
        }
    }
}

