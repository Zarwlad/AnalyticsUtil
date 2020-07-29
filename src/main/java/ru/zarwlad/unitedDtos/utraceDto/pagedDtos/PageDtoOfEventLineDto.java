package ru.zarwlad.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventLineDto;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageDtoOfEventLineDto {

    @JsonProperty(value = "data")
    List<EventLineDto> eventLineDtos;

    @JsonProperty(value = "page")
    Page page;
}
