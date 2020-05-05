package ru.zarwlad.jira.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.zarwlad.jira.incomingJiraDto.issues.Issue;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws IOException {
        String apiToken = Base64
                .getEncoder()
                .encodeToString(args[0].getBytes());

        String authValue = "Basic " + apiToken;

        OkHttpClient okHttpClient = new OkHttpClient();

        Request requestGetTask = new Request.Builder()
                .url("https://utrace.atlassian.net/rest/api/3/issue/UTH-4337/changelog")
                .get()
                .addHeader("Authorization", authValue)
                .build();

        Response response = okHttpClient.newCall(requestGetTask).execute();

        ResponseBody responseBody = response.body();

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        assert responseBody != null;
        Issue issue = objectMapper.readValue(responseBody.string(), Issue.class);

        System.out.println(responseBody.string());

        responseBody.close();

    }
}
