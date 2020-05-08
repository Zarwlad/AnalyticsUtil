package ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto;

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
public class AddressResolvedShortDto {
    @JsonProperty(value = "address")
    String address;

    @JsonProperty(value = "code")
    int code;
}
