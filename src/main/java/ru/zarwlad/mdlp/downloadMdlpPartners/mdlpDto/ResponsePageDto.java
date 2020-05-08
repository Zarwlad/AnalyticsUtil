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
public class ResponsePageDto {
    @JsonProperty(value = "filtered_records")
    private FilteredRecordsDto filteredRecordsDto;

    @JsonProperty(value = "filtered_records_count")
    private int filteredRecordsCount;
}
