package ru.zarwlad.utrace.model;

public class Auth {
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
}
