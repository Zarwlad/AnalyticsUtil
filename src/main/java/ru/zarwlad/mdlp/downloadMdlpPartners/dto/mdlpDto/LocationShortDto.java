package ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto;

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
public class LocationShortDto {
    @JsonProperty
    String id;

    @JsonProperty(value = "address_fias")
    AddressFiasShortDto addressFiasShortDto;

    @JsonProperty(value = "address_resolved")
    AddressResolvedShortDto addressResolvedShortDto;

    @JsonProperty("status")
    int status;

}
