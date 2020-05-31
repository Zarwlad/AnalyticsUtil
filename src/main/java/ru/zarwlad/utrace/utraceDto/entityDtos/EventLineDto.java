package ru.zarwlad.utrace.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonAutoDetect
@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventLineDto {
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
