package ru.zarwlad.unitedDtos.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AddressFiasShortDto {
    @JsonProperty(value = "houseguid")
    String houseguid;

    @JsonProperty(value = "aoguid")
    String aoguid;
}
