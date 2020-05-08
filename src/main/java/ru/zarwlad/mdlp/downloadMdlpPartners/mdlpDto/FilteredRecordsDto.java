package ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class FilteredRecordsDto {
    @JsonProperty
    private List<BusinessPartnerDto> businessPartnerDtos;
}
