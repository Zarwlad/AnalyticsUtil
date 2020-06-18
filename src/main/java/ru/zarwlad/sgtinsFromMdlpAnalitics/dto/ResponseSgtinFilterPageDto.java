package ru.zarwlad.sgtinsFromMdlpAnalitics.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
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
