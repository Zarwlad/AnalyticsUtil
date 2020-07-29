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
@EqualsAndHashCode(of = "sscc")
public class SsccDto {
    String sscc;

    @JsonProperty(value = "release_date")
    String releaseDate;

    @JsonProperty(value = "system_subj_id")
    String systemSubjectId;
}
