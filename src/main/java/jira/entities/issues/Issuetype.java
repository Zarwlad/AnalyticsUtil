package jira.entities.issues;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.net.URL;

@JsonAutoDetect
class Issuetype{
    private URL self;
    private String id;
    private String name;
}
