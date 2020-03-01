package jira.utils;

import okhttp3.OkHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class OkHttpConnectionUtil {

    public OkHttpClient getOkHttpClient(){
        return new OkHttpClient();
    }

    public String getApiAuth(){
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(new File("C:\\Users\\Vladimir\\IdeaProjects\\AnalyticsUtil\\src\\main\\resources\\application.properties\\application.properties")));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        String apiToken = Base64
                .getEncoder()
                .encodeToString(properties.getProperty("loginToken").getBytes());

        return "Basic " + apiToken;
    }
}
