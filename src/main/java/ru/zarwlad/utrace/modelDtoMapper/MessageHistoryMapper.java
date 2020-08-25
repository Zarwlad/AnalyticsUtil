package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.MessageHistoryDto;
import ru.zarwlad.utrace.model.MessageHistory;
import ru.zarwlad.utrace.util.DateTimeUtil;

import java.util.UUID;

public class MessageHistoryMapper {
    public static MessageHistory fromDtoToEntity(MessageHistoryDto messageHistoryDto) {
        MessageHistory messageHistory = new MessageHistory();
        messageHistory.setId(UUID.fromString(messageHistoryDto.getId()));
        messageHistory.setAuthoredBy(messageHistoryDto.getAuthoredBy());
        messageHistory.setCreated(DateTimeUtil.toZonedDateTime(messageHistoryDto.getCreated()));
        messageHistory.setStatus(messageHistoryDto.getStatus());
        return messageHistory;
    }
}
