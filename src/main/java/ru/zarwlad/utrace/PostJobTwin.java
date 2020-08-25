package ru.zarwlad.utrace;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfBriefedBusinessEventDto;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;

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

public class PostJobTwin {
    static Logger log = LoggerFactory.getLogger(PostJobTwin.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<String> evFilter = new ArrayList<>();
        evFilter.add("&size=200");
        evFilter.add("&sort=created,asc");
        evFilter.add("&priority=0");
        evFilter.add("&status=FILLED");

        StringBuilder filtersForLog = new StringBuilder();
        evFilter.forEach(filtersForLog::append);
        log.info("Извлекаю данные по фильтрам: {}", filtersForLog.toString());
        PageDtoOfBriefedBusinessEventDto events = UtraceClient.getPagedEventsByFilter(evFilter);
        log.info("Извлечено событий: {}", events.getPage().getSize());

        List<EventDtoStatusProcessing> processings = new ArrayList<>();
        for (EventDto event : events.getData()) {
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

            if (s.contains("504")){
                processing.setStatus(ProcStatus.TIME_OVER);
                log.info("Событие {}, status {}", event.getId(), processing.getStatus());

                processings.add(processing);

                log.info("Событие {}, засыпаю на {}", event.getId(), 300_000);
                Thread.sleep(300_000);
            }
            else if (s.contains("{}")){
                processing.setStatus(ProcStatus.SUCCESS);
                log.info("Событие {}, status {}", event.getId(), processing.getStatus());
            }
            else {
                processing.setStatus(ProcStatus.FAILED);
                log.info("Событие {}, status {}", event.getId(), processing.getStatus());
                processing.setErrorText(s);
            }
            processings.add(processing);
        }

        for (EventDtoStatusProcessing processing : processings) {
            if (ProcStatus.TIME_OVER.equals(processing.getStatus())){
                EventDto eventDto = UtraceClient.getEventById(processing.getEventDto().getId());
                if (eventDto.getStatus().equals("POSTED")){
                    processing.setStatus(ProcStatus.SUCCESS);
                }
                else if (eventDto.getStatus().equals("WAITING")){
                    processing.setStatus(ProcStatus.ERROR_TIME_OVER);
                }
            }
        }

        String t = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm-ss"));
        Path p = Paths.get(String.format("src/reports/result_%s.csv", t));
        if (!Files.exists(p)){
            Files.createFile(p);
        }

        Files.writeString(p,
                "eventId;"
                + "eventType;"
                + "eventCreated;"
                + "startProcessing;"
                + "endProcessing;"
                + "durationSecs;"
                + "errorText"
                + "statusProcessing"
                +  "eventStatus\n",
                StandardOpenOption.APPEND);

        for (EventDtoStatusProcessing processing : processings) {
            Files.writeString(p,
                    processing.getEventDto().getId() + ";"
                            + processing.getEventDto().getType() + ";"
                            + processing.getEventDto().getCreated() + ";"
                            + processing.getStartProcessing() + ";"
                            + processing.getEndProcessing() + ";"
                            + processing.getDuration().getSeconds() + ";"
                            + processing.getErrorText()+ ";"
                            + processing.getStatus()+ ";"
                            + processing.getEventDto().getStatus()+ ";"
                            + "\n",
                    StandardOpenOption.APPEND);
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