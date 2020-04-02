package utrace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import utrace.data.AuthData;
import utrace.dto.AuthDto;
import utrace.dto.PostAuthDto;
import utrace.entities.Auth;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AuthService {
    static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\java\\utrace\\service\\app.properties")));
        } catch (IOException e) {
            e.printStackTrace();
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

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(postAuthDto));
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
