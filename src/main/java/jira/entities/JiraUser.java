package jira.entities;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
public class JiraUser {
    @JsonProperty("accountId")
    String id;

    @JsonProperty("name")
    String name;
}
