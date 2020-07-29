package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.AuditRecordDto;

public class EventStatusMapper {
    public static EventStatus fromDtoToEntity(AuditRecordDto auditRecordDto){
        return new EventStatus(
                auditRecordDto.getSnapshot().getStatus(),
                DateTimeUtil.toZonedDateTime(auditRecordDto.getOperationDateTime()));
    }
}
