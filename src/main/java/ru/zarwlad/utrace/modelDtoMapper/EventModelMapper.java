package ru.zarwlad.utrace.modelDtoMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventDto;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class EventModelMapper {
    private static Logger log = LoggerFactory.getLogger(EventModelMapper.class);

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static Event fromDtoToEntity(EventDto eventDto){
        Event event = new Event();
        event.setId(UUID.fromString(eventDto.getId()));
        event.setOperationDate(DateTimeUtil.toZonedDateTime(eventDto.getOperationDate()));
        event.setClient(properties.getProperty("client"));
        event.setRegulatorStatus(eventDto.getRegulatorStatus());
        event.setStatus(eventDto.getStatus());
        event.setType(eventDto.getType());
        event.setCreatedDate(DateTimeUtil.toZonedDateTime(eventDto.getCreated()));
        return event;
    }
}
