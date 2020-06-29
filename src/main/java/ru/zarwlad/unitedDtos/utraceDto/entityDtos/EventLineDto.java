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
public class EventLineDto implements Dto {
    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "event")
    EventDto eventDto;

    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "isSscc")
    boolean sscc;

    @JsonProperty(value = "parent")
    EventLineDto parentLineDto;
}
