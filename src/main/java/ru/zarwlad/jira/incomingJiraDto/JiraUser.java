package ru.zarwlad.jira.incomingJiraDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.net.URL;

@JsonAutoDetect
public class JiraUser {
    URL self;
    String name;
    String accountId;
    String displayName;

    public JiraUser(URL self, String name, String accountId, String displayName) {
        this.self = self;
        this.name = name;
        this.accountId = accountId;
        this.displayName = displayName;
    }

    public JiraUser() {
    }

    public URL getSelf() {
        return self;
    }

    public void setSelf(URL self) {
        this.self = self;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "JiraUser{" +
                "self=" + self +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
