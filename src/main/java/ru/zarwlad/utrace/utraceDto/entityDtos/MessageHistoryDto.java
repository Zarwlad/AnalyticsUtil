package ru.zarwlad.utrace.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.model.MessageHistory;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class MessageHistoryDto implements Dto {

    @JsonProperty(value = "authoredBy")
    String authoredBy;

    @JsonProperty(value = "created")
    String created;

    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "message")
    MessageDto messageDto;

    @JsonProperty(value = "status")
    String status;

    public Object fromDtoToEntity() {
        MessageHistoryDto messageHistoryDto = (MessageHistoryDto) this;
        return new MessageHistory(messageHistoryDto.getId(),
                messageHistoryDto.getAuthoredBy(),
                DateTimeUtil.toZonedDateTime(messageHistoryDto.getCreated()),
                messageHistoryDto.getStatus());
    }
}
