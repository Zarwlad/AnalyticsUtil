package ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.auth;

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
public class ResponseAuthCodeMdlpDto {
    @JsonProperty(value = "code")
    String code;
}
