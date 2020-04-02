package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class PostAuthDto implements Dto {
    @JsonProperty(value = "login")
    String login;

    @JsonProperty(value = "password")
    String password;

    public PostAuthDto() {
    }

    public PostAuthDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object fromDtoToEntity() {
        return null;
    }
}
