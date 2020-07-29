package ru.zarwlad.unitedDtos.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FilterForRequestDto {
    List<String> status;
    String batch;
    String gtin;

    @JsonProperty("emission_type")
    List<String> emissionTypes;
}
