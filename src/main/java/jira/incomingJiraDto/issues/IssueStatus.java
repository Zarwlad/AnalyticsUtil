package jira.incomingJiraDto.issues;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
class IssueStatus{
    String status;
}