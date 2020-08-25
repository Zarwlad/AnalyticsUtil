package ru.zarwlad.utrace.unitedDtos.mdlpDto;

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
public class ResponseBpPageDto {
    @JsonProperty(value = "filtered_records")
    private List<BusinessPartnerDto> businessPartnerDtos;

    @JsonProperty(value = "filtered_records_count")
    private int filteredRecordsCount;
}
