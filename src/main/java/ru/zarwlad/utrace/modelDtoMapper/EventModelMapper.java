package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventDto;

public class EventModelMapper {
    public static Event fromDtoToEntity(EventDto eventDto){
        return new Event(eventDto.getId(),
                eventDto.getType(),
                DateTimeUtil.toZonedDateTime(eventDto.getOperationDate()),
                eventDto.getStatus(),
                eventDto.getRegulatorStatus(),
                null,
                null,
                DateTimeUtil.toZonedDateTime(eventDto.getCreated()));
    }
}
