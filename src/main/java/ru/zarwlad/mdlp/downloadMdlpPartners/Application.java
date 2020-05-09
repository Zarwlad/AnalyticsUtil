package ru.zarwlad.mdlp.downloadMdlpPartners;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpAuth.MdlpAuthService;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;
import ru.zarwlad.mdlp.downloadMdlpPartners.service.DownloadMdlpService;
import ru.zarwlad.mdlp.downloadMdlpPartners.service.SaveDbService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Application {
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        System.out.println(MdlpAuthService.getMdlpSession().toString());

        List<BusinessPartner> businessPartners = DownloadMdlpService.downloadBusinessPartners();

    }
}
