package ru.zarwlad.utrace.data;

import ru.zarwlad.utrace.model.Auth;

public class AuthData {
    private Auth auth;

    private static AuthData instance;

    private AuthData(){
    }

    public static AuthData getInstance() {
        if (instance == null){
            instance = new AuthData();
        }
        return instance;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
