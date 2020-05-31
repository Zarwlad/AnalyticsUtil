package ru.zarwlad.utrace.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.zarwlad.utrace.model.Auth;
import ru.zarwlad.utrace.utraceDto.Dto;

@JsonAutoDetect
public class AuthDto implements Dto {
    @JsonProperty(value = "accessToken")
    private String accessToken;

    public AuthDto() {
    }

    public AuthDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public Object fromDtoToEntity() {
        AuthDto authDto = (AuthDto) this;
        return new Auth(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
