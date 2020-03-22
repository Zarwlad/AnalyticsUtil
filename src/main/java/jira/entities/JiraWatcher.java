package jira.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "watcher")
public class JiraWatcher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @JoinColumn(name = "jira_user_id")
    UUID jiraUser;

    @JoinColumn(name = "jira_issue_id")
    UUID jiraIssue;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "last_modified_date")
    Timestamp lastModifiedDate;

    public JiraWatcher() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getJiraUser() {
        return jiraUser;
    }

    public void setJiraUser(UUID jiraUser) {
        this.jiraUser = jiraUser;
    }

    public UUID getJiraIssue() {
        return jiraIssue;
    }

    public void setJiraIssue(UUID jiraIssue) {
        this.jiraIssue = jiraIssue;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
