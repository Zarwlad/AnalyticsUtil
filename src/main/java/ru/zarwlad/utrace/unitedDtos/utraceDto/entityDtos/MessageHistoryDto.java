package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
