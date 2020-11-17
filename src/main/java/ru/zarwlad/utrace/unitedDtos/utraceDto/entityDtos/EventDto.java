package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(of = "id")
public class EventDto implements Dto {
    @JsonProperty(value = "id")
    @ToString.Include
    String id;

    @JsonProperty(value = "type")
    @ToString.Include
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
