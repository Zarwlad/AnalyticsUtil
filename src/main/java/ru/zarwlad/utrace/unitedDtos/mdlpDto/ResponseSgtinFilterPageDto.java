package ru.zarwlad.utrace.unitedDtos.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.util.List;

@JsonAutoDetect
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResponseSgtinFilterPageDto {
    Integer total;
    List<SgtinFilterMdlpDto> entries;

}
