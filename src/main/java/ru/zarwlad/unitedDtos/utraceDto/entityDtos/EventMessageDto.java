package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class EventMessageDto implements Dto {
    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "direction")
    String direction;

    @JsonProperty(value = "externalSystemId")
    String externalSystemId;

    @JsonProperty(value = "messageId")
    String messageId;

    @JsonProperty(value = "businessEvent")
    EventDto event;
}
