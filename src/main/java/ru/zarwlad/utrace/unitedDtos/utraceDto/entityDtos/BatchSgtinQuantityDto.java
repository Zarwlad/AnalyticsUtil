package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.BatchBriefDto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BatchSgtinQuantityDto {
    @JsonProperty(value = "batch")
    @CsvRecurse
    BatchBriefDto batchBriefDto;

    @CsvBindByName
    Integer quantity;
}
