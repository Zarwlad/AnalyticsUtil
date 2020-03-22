package jira.dto.issues;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.net.URL;

@JsonAutoDetect
class Issuetype{
    private URL self;
    private String id;
    private String name;
}
