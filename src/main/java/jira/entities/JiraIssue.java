package jira.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "jira_issue")
public class JiraIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "key", unique = true, nullable = false)
    String key;

    @JoinColumn(name = "assignee_id", nullable = false)
    JiraUser assignee;

    @JoinColumn(name = "ba_analyst_id")
    JiraUser baAnalyst;

    @Column(name = "created_date", nullable = false)
    Timestamp createdDate;

    @JoinColumn(name = "creator_id", nullable = false)
    JiraUser creator;

    @JoinColumn(name = "developer_id")
    JiraUser developer;

    @Column(name = "last_modified_date", nullable = false)
    Timestamp lastModifiedDate;

    @JoinColumn(name = "qa_id")
    JiraUser qa;

    @Column(name = "release_version")
    String releaseVersions;

    @JoinColumn(name = "sa_analyst_id")
    JiraUser saAnalyst;

    @JoinColumn(name = "sa_reviewer_id")
    JiraUser saReviewer;

    @Column(name = "type", nullable = false)
    String type;

    public JiraIssue() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JiraUser getAssignee() {
        return assignee;
    }

    public void setAssignee(JiraUser assignee) {
        this.assignee = assignee;
    }

    public JiraUser getBaAnalyst() {
        return baAnalyst;
    }

    public void setBaAnalyst(JiraUser baAnalyst) {
        this.baAnalyst = baAnalyst;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public JiraUser getCreator() {
        return creator;
    }

    public void setCreator(JiraUser creator) {
        this.creator = creator;
    }

    public JiraUser getDeveloper() {
        return developer;
    }

    public void setDeveloper(JiraUser developer) {
        this.developer = developer;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public JiraUser getQa() {
        return qa;
    }

    public void setQa(JiraUser qa) {
        this.qa = qa;
    }

    public String getReleaseVersions() {
        return releaseVersions;
    }

    public void setReleaseVersions(String releaseVersions) {
        this.releaseVersions = releaseVersions;
    }

    public JiraUser getSaAnalyst() {
        return saAnalyst;
    }

    public void setSaAnalyst(JiraUser saAnalyst) {
        this.saAnalyst = saAnalyst;
    }

    public JiraUser getSaReviewer() {
        return saReviewer;
    }

    public void setSaReviewer(JiraUser saReviewer) {
        this.saReviewer = saReviewer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
