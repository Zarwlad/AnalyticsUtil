package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageDto;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.util.DateTimeUtil;

import java.util.UUID;

public class MessageModelMapper {
    public static Message fromDtoToEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setId(UUID.fromString(messageDto.getId()));
        message.setCreatedDate(DateTimeUtil.toZonedDateTime(messageDto.getCreated()));
        message.setOperationDate(DateTimeUtil.toZonedDateTime(messageDto.getOperationDate()));
        message.setStatus(messageDto.getStatus());
        message.setDocumentTypeId(messageDto.getDocumentType().getId());
        message.setForMdlp(true);
        return message;
    }
}
