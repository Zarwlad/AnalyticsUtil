package jira.entities.issues;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.net.URL;

@JsonAutoDetect
class Watch{
    private URL self;
    private Integer watchCount;
    private Boolean isWatching;
}