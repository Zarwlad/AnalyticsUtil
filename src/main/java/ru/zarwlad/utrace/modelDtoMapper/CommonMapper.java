package ru.zarwlad.utrace.modelDtoMapper;

import ru.zarwlad.unitedDtos.utraceDto.entityDtos.AuditRecordDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageHistoryDto;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.model.MessageHistory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CommonMapper {
    public static Event mapEventDtosToEntities(EventDto eventDto,
                                              Set<AuditRecordDto> auditRecordDtos,
                                              Set<MessageDto> messageDtos,
                                              Map<MessageHistoryDto, MessageDto> messageHistoryMap){
        Event event = EventModelMapper.fromDtoToEntity(eventDto);
        Set<EventStatus> eventStatuses = new HashSet<>();

        for (AuditRecordDto auditRecordDto : auditRecordDtos) {
            eventStatuses.add(EventStatusMapper.fromDtoToEntity(auditRecordDto));
        }
        for (EventStatus eventStatus : eventStatuses) {
            eventStatus.setEvent(event);
        }
        event.setEventStatuses(eventStatuses);

        Set<Message> messages = new HashSet<>();
        for (MessageDto messageDto : messageDtos) {
            Message msg = mapMsgDtosToEntities(messageDto, messageHistoryMap);
            messages.add(msg);
        }

        for (Message message : messages) {
            message.setEvent(event);
        }
        event.setMessages(messages);

        return event;
    }

    public static Message mapMsgDtosToEntities(MessageDto messageDto,
                                                Map<MessageHistoryDto, MessageDto> messageHistoryMap){
        Message message = MessageModelMapper.fromDtoToEntity(messageDto);

        Set<MessageHistoryDto> trueHistoryDtos = new HashSet<>();

        for (Map.Entry<MessageHistoryDto, MessageDto> entry : messageHistoryMap.entrySet()) {
            if (UUID.fromString(entry.getValue().getId()).equals(message.getId()))
                trueHistoryDtos.add(entry.getKey());
        }

        Set<MessageHistory> messageHistories = new HashSet<>();

        for (MessageHistoryDto messageHistoryDto : trueHistoryDtos) {
            MessageHistory messageHistory = MessageHistoryMapper.fromDtoToEntity(messageHistoryDto);
            messageHistories.add(messageHistory);
        }

        for (MessageHistory messageHistory : messageHistories) {
            messageHistory.setMessage(message);
        }

        message.setMessageHistories(messageHistories);
        return message;
    }

}
