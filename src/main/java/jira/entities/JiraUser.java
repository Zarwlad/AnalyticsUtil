package jira.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "jira_user")
public class JiraUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String name;

    @Column(name = "jira_id")
    String jiraId;

    String login;

    public JiraUser() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJiraId() {
        return jiraId;
    }

    public void setJiraId(String jiraId) {
        this.jiraId = jiraId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
