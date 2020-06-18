package ru.zarwlad.mdlp.downloadMdlpPartners.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpAuth.MdlpAuthService;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.BusinessPartnerDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.ResponseBpPageDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dtoModelMapper.BusinessPartnerMapper;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DownloadMdlpService {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120_000, TimeUnit.MILLISECONDS)
            .build();

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger log = LoggerFactory.getLogger(DownloadMdlpService.class);

    public static List<BusinessPartner> downloadBusinessPartners(){
        int totalRecords = 0;
        int startFrom = 59060;
        int pageSize = 80;
        ResponseBpPageDto responseBpPageDto = getBpMdlpPage(startFrom, pageSize);

        totalRecords = responseBpPageDto.getFilteredRecordsCount();

        List<BusinessPartner> businessPartners = new ArrayList<>();

        for (BusinessPartnerDto businessPartnerDto : responseBpPageDto.getBusinessPartnerDtos()) {
            businessPartners.add(BusinessPartnerMapper.fromDtoToEntity(businessPartnerDto));
        }

        while (businessPartners.size() < totalRecords){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startFrom = startFrom + pageSize;

            responseBpPageDto = getBpMdlpPage(startFrom, pageSize);

            for (BusinessPartnerDto businessPartnerDto : responseBpPageDto.getBusinessPartnerDtos()) {
                businessPartners.add(BusinessPartnerMapper.fromDtoToEntity(businessPartnerDto));
            }
        }
        return businessPartners;
    }

    private static ResponseBpPageDto getBpMdlpPage(int currentPage, int pageSize){
        String url = properties.getProperty("hostMdlp") + "/api/v1/reestr_partners/filter";

        MediaType mediaType = MediaType.parse("application/json");

        String body = String.format("{" +
                "    \"filter\": {" +
                "        \"reg_entity_type\": 1," +
                "        \"full\": true" +
                "    }," +
                "    \"start_from\": %d," +
                "    \"count\": %d" +
                "}",
                currentPage, pageSize);

        RequestBody requestBody = RequestBody.create(mediaType, body);

        String token = "token " + MdlpAuthService.getMdlpSession().getMdlpToken();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseBody responseBody = response.body();

        ResponseBpPageDto responseBpPageDto = null;
        try {
            String responseBodyStr = responseBody.string();

            log.info("Запрос: " + request.toString());
            log.info("Тело запроса: " + body);
            log.info("Ответ: " + response.toString());
            log.info("Тело ответа: " + responseBodyStr);

            responseBpPageDto = objectMapper.readValue(responseBodyStr, ResponseBpPageDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBpPageDto;
    }
}
