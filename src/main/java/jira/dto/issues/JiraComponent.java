package jira.dto.issues;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
class JiraComponent {
    String id;
    String name;
}