package ru.zarwlad.utrace.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class EventDto implements Dto {
    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "type")
    String type;

    @JsonProperty(value = "operationDate")
    String operationDate;

    @JsonProperty(value = "status")
    String status;

    @JsonProperty(value = "regulatorStatus")
    String regulatorStatus;

    @JsonProperty(value = "created")
    String created;
}
