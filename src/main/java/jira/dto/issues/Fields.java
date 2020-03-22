package jira.dto.issues;

import jira.dto.JiraUser;
import jira.dto.Project;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.util.ArrayList;

@JsonAutoDetect
class Fields{
    private Project project;
    private String summary; //заголовок задачи
    private String description; //текст задачи

    private JiraUser creator; //создатель
    private JiraUser assignee; //на ком задача назначена

    private Issuetype issuetype;
    private IssueStatus status;
    private Priority priority;

    private String statuscategorychangedate;
    private String created;
    private String updated;
    private String lastViewed;

    private ArrayList<String> fixVersions; //версия релиза
    private ArrayList<String> versions;

    private String timespent;

    private String resolutiondate;
    private Watch watches;

    private ArrayList<String> labels; //метки
    private ArrayList<Issue> issuelinks; //связанные задачи, не эпики
    private ArrayList<JiraComponent> components;


    public Fields(Project project,
                  String summary,
                  String description,
                  JiraUser creator,
                  JiraUser assignee,
                  Issuetype issuetype,
                  IssueStatus status,
                  Priority priority,
                  String statuscategorychangedate,
                  String created,
                  String updated,
                  String lastViewed,
                  ArrayList<String> fixVersions,
                  ArrayList<String> versions,
                  String timespent, String resolutiondate,
                  Watch watches,
                  ArrayList<String> labels,
                  ArrayList<Issue> issuelinks,
                  ArrayList<JiraComponent> components) {
        this.project = project;
        this.summary = summary;
        this.description = description;
        this.creator = creator;
        this.assignee = assignee;
        this.issuetype = issuetype;
        this.status = status;
        this.priority = priority;
        this.statuscategorychangedate = statuscategorychangedate;
        this.created = created;
        this.updated = updated;
        this.lastViewed = lastViewed;
        this.fixVersions = fixVersions;
        this.versions = versions;
        this.timespent = timespent;
        this.resolutiondate = resolutiondate;
        this.watches = watches;
        this.labels = labels;
        this.issuelinks = issuelinks;
        this.components = components;
    }

    public Fields() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JiraUser getCreator() {
        return creator;
    }

    public void setCreator(JiraUser creator) {
        this.creator = creator;
    }

    public JiraUser getAssignee() {
        return assignee;
    }

    public void setAssignee(JiraUser assignee) {
        this.assignee = assignee;
    }

    public Issuetype getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getStatuscategorychangedate() {
        return statuscategorychangedate;
    }

    public void setStatuscategorychangedate(String statuscategorychangedate) {
        this.statuscategorychangedate = statuscategorychangedate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(String lastViewed) {
        this.lastViewed = lastViewed;
    }

    public ArrayList<String> getFixVersions() {
        return fixVersions;
    }

    public void setFixVersions(ArrayList<String> fixVersions) {
        this.fixVersions = fixVersions;
    }

    public ArrayList<String> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<String> versions) {
        this.versions = versions;
    }

    public String getTimespent() {
        return timespent;
    }

    public void setTimespent(String timespent) {
        this.timespent = timespent;
    }

    public String getResolutiondate() {
        return resolutiondate;
    }

    public void setResolutiondate(String resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    public Watch getWatches() {
        return watches;
    }

    public void setWatches(Watch watches) {
        this.watches = watches;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<Issue> getIssuelinks() {
        return issuelinks;
    }

    public void setIssuelinks(ArrayList<Issue> issuelinks) {
        this.issuelinks = issuelinks;
    }

    public ArrayList<JiraComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<JiraComponent> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", assignee=" + assignee +
                ", issuetype=" + issuetype +
                ", status=" + status +
                ", priority=" + priority +
                ", statuscategorychangedate=" + statuscategorychangedate +
                ", created=" + created +
                ", updated=" + updated +
                ", lastViewed=" + lastViewed +
                ", fixVersions=" + fixVersions +
                ", versions=" + versions +
                ", timespent='" + timespent + '\'' +
                ", resolutiondate='" + resolutiondate + '\'' +
                ", watches=" + watches +
                ", labels=" + labels +
                ", issuelinks=" + issuelinks +
                ", components=" + components +
                '}';
    }
}