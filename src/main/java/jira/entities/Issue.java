package jira.entities;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

@JsonAutoDetect
public class Issue {
    private Project project;
    private String id;
    private String key;
    private Fields fields;

    private class Fields{
        private Date statuscategorychangedate;
        private Issuetype issuetype;
        private String timespent;
        private String customfield_10027;
        private String customfield_10028;
        private String customfield_10029;
        private String customfield_10030;
        private String customfield_10031;
        private String customfield_10032;
        private String customfield_10035;
        private String customfield_10036;
        private String customfield_10037;
        private ArrayList<String> fixVersions;
        private String resolutiondate;


        private class Issuetype{
            private URL self;
            private String id;
            private String name;
        }
    }
}

