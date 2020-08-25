package ru.zarwlad.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.CodeDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventStateDto;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageEventStateDto {
    @JsonProperty(value = "data")
    List<EventStateDto> eventStateDtos;

    @JsonProperty(value = "page")
    Page page;
}
