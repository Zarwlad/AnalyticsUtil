package jira.entities;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URL;
import java.util.Date;

@JsonAutoDetect
public class Issue {
    private String id;
    private String key;
    private Fields fields;



    private class Fields{
        private Date statuscategorychangedate;
        private Issuetype issuetype;


        private class Issuetype{
            private URL self;
            private String id;
            private String name;
            
        }
    }
}

