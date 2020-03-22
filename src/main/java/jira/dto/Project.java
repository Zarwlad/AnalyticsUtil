package jira.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.net.URL;

@JsonAutoDetect
public class Project {
    private URL self;
    private String id;
    private String key;
    private String name;
}
