package jira.incomingJiraDto.issues;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Issue {
    private String id;
    private String key;
    private Fields fields;

    @Override
    public String toString() {
        return "Issue{" +
                ", id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", fields=" + fields +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Issue() {
    }

    public Issue(String id, String key, Fields fields) {
        this.id = id;
        this.key = key;
        this.fields = fields;
    }
}

