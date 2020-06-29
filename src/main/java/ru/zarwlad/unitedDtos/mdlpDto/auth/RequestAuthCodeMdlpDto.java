package ru.zarwlad.unitedDtos.mdlpDto.auth;

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
public class RequestAuthCodeMdlpDto {
    @JsonProperty(value = "client_id")
    String clientId;

    @JsonProperty(value = "client_secret")
    String clientSecret;

    @JsonProperty(value = "user_id")
    String userId;

    @JsonProperty(value = "auth_type")
    String authType;
}
