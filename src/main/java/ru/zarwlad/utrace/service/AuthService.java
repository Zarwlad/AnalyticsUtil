package ru.zarwlad.utrace.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.data.AuthData;
import ru.zarwlad.utrace.utraceDto.entityDtos.AuthDto;
import ru.zarwlad.utrace.utraceDto.entityDtos.PostAuthDto;
import ru.zarwlad.utrace.model.Auth;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AuthService {
    private static Logger log = LoggerFactory.getLogger(EventStatisticCounterService.class);

    static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static void Auth() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введи логин");
        String login = bufferedReader.readLine();
        System.out.println("Введи пароль");
        String password = bufferedReader.readLine();

        PostAuthDto postAuthDto = new PostAuthDto(login, password);

        String urlPath = properties.getProperty("host") + "api/" + "auth";

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                objectMapper.writeValueAsString(postAuthDto));

        Request request = new Request.Builder()
                .post(requestBody)
                .url(urlPath)
                .addHeader("locale", "ru-RU")
                .build();

        Response response = okHttpClient.newCall(request).execute();

        ResponseBody responseBody = response.body();

        String responseBodyStr = responseBody.string();
        responseBody.close();

        AuthDto authDto = objectMapper.readValue(responseBodyStr, AuthDto.class);

        AuthData authData = AuthData.getInstance();
        authData.setAuth((Auth) authDto.fromDtoToEntity());
    }
}
