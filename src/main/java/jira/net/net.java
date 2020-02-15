package jira.net;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

public class net {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String apiToken = Base64
                .getEncoder()
                .encodeToString("vzaremba@navicons.ru:Fkrlo365XDbB3D78q2Vn9ABF".getBytes());

        String authValue = "Basic " + apiToken;

        OkHttpClient okHttpClient = new OkHttpClient();

        Request requestGetTask = new Request.Builder()
                .url("https://utrace.atlassian.net/rest/api/2/issue/UTH-4337")
                .get()
                .addHeader("Authorization", authValue)
                //.addHeader("Content-Type", "application/json")
                .build();

        Response response = okHttpClient.newCall(requestGetTask).execute();

        ResponseBody responseBody = response.body();

        System.out.println(responseBody.string());

        responseBody.close();



    }
}
