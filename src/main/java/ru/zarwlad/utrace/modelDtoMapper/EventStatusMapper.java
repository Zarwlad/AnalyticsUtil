package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.AuditRecordDto;

public class EventStatusMapper {
    public static EventStatus fromDtoToEntity(AuditRecordDto auditRecordDto){
        EventStatus eventStatus = new EventStatus();
        eventStatus.setChangeOperationDate(DateTimeUtil.toZonedDateTime(auditRecordDto.getOperationDateTime()));
        eventStatus.setId(auditRecordDto.getId());
        eventStatus.setStatus(auditRecordDto.getSnapshot().getStatus());

        return eventStatus;
    }
}
