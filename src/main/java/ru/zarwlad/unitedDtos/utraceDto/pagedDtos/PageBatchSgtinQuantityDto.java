package ru.zarwlad.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.BatchSgtinQuantityDto;
import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PageBatchSgtinQuantityDto {
    @JsonProperty(value = "data")
    List<BatchSgtinQuantityDto> batchSgtinQuantityDtos;

    @JsonProperty(value = "page")
    Page page;
}
