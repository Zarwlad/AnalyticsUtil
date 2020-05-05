package ru.zarwlad.utrace.model;

import ru.zarwlad.utrace.utraceDto.Dto;

public class Auth implements Entity {
    String accessToken;

    public Auth() {
    }

    public Auth(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return null;
    }
}
