package ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.CodeTreeRootDto;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageDtoOfCodeTreeRootDto {

    @JsonProperty(value = "data")
    List<CodeTreeRootDto> codeTreeRootDtos;

    @JsonProperty(value = "page")
    Page page;
}
