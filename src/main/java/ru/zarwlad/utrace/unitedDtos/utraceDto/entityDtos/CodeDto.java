package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.BatchBriefDto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class CodeDto {
    String id;

    String createdDate;

    String gtdNumber;

    String sgtinOrSscc;

    Boolean sscc;

    String value;

    @JsonProperty(value = "batch")
    BatchBriefDto batchBriefDto;
}
