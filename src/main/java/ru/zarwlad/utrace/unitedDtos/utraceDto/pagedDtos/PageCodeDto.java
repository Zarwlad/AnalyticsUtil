package ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.CodeDto;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageCodeDto {
    @JsonProperty(value = "data")
    List<CodeDto> codeDtos;

    @JsonProperty(value = "page")
    Page page;
}
