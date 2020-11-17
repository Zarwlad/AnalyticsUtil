package ru.zarwlad.utrace.unitedDtos.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RequestSgtinFilterDto {
    @JsonProperty(value = "filter")
    FilterForRequestDto filter;

    Integer count;

    @JsonProperty(value = "start_from")
    Integer startFrom;

    String sort;

}
