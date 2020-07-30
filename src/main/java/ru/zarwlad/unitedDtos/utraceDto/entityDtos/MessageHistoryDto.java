package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.model.MessageHistory;
import ru.zarwlad.utrace.modelDtoMapper.MessageModelMapper;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

import java.util.UUID;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class MessageHistoryDto {

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

}
