package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.AuditRecordDto;

import java.util.UUID;

public class EventStatusMapper {
    public static EventStatus fromDtoToEntity(AuditRecordDto auditRecordDto){
        EventStatus eventStatus = new EventStatus();
        eventStatus.setChangeOperationDate(DateTimeUtil.toZonedDateTime(auditRecordDto.getOperationDateTime()));
        eventStatus.setId(UUID.fromString(auditRecordDto.getSnapshot().getId()));
        eventStatus.setStatus(auditRecordDto.getSnapshot().getStatus());

        return eventStatus;
    }
}
