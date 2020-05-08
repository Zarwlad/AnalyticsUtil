package ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto.auth;

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
public class RequestAuthTokenMdlpDto {
    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "password")
    String password;
}
