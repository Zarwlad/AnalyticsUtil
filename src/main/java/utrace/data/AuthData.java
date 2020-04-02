package utrace.data;

import utrace.entities.Auth;
import utrace.entities.Event;

import java.util.HashSet;
import java.util.Set;

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
