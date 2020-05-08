package ru.zarwlad.mdlp.downloadMdlpPartners.mdlpAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto.auth.RequestAuthCodeMdlpDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto.auth.RequestAuthTokenMdlpDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto.auth.ResponseAuthCodeMdlpDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpDto.auth.ResponseAuthTokenMdlpDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MdlpAuthService {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static MdlpSession mdlpSession = null;

    public MdlpSession getMdlpSession(){
        return mdlpSession;
    }

    private static void initializeMlpSession(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration checkTokenExpires = Duration.between(currentDateTime, mdlpSession.getCreatedDate());

        if (mdlpSession == null ||
                mdlpSession.getTokenLifetime()*60-1 <= checkTokenExpires.getSeconds()){
            ResponseAuthCodeMdlpDto responseCode = getMdlpCode();
            ResponseAuthTokenMdlpDto responseAuthToken = getMdlpToken(responseCode);

            if (mdlpSession == null)
                mdlpSession = new MdlpSession();

            mdlpSession.setMdlpToken(responseAuthToken.getToken());
            mdlpSession.setCreatedDate(LocalDateTime.now());
            mdlpSession.setTokenLifetime(responseAuthToken.getLifeTime());
        }
    }

    private static ResponseAuthCodeMdlpDto getMdlpCode(){
        String url = properties.getProperty("hostMdlp") + "/api/v1/auth";

        RequestAuthCodeMdlpDto requestAuthCodeMdlpDto = new RequestAuthCodeMdlpDto(
                properties.getProperty("clientIdMdlp"),
                properties.getProperty("clientSecretMdlp"),
                properties.getProperty("userIdMdlp"),
                properties.getProperty("authTypeMdlp"));

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(
                    mediaType,
                    objectMapper.writeValueAsString(requestAuthCodeMdlpDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = null;
        try {
             response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseBody responseBody = response.body();

        ResponseAuthCodeMdlpDto responseAuthCodeMdlpDto = null;
        try {
            responseAuthCodeMdlpDto = objectMapper.readValue(
                    responseBody.string(),
                    ResponseAuthCodeMdlpDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseAuthCodeMdlpDto;
    }

    private static ResponseAuthTokenMdlpDto getMdlpToken(ResponseAuthCodeMdlpDto responseAuthCodeMdlpDto){
        String url = properties.getProperty("hostMdlp") + "/api/v1/token";

        RequestAuthTokenMdlpDto requestAuthTokenMdlpDto = new RequestAuthTokenMdlpDto(
                responseAuthCodeMdlpDto.getCode(),
                properties.getProperty("passwordMdlp"));

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(
                    mediaType,
                    objectMapper.writeValueAsString(requestAuthTokenMdlpDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseBody responseBody = response.body();

        ResponseAuthTokenMdlpDto responseAuthTokenMdlpDto = null;
        try {
            responseAuthTokenMdlpDto = objectMapper.readValue(
                    responseBody.string(),
                    ResponseAuthTokenMdlpDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseAuthTokenMdlpDto;
    }

}
