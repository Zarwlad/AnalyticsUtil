package ru.zarwlad.util.client;

import okhttp3.*;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.mdlpAuth.MdlpAuthService;
import ru.zarwlad.unitedDtos.mdlpDto.*;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfCodeTreeRootDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;

import java.io.IOException;

import static ru.zarwlad.util.client.ObjectMapperConfig.objectMapper;
import static ru.zarwlad.util.client.OkHttpConfig.okHttpClient;
import static ru.zarwlad.util.client.PropertiesConfig.properties;
import static ru.zarwlad.util.client.UtraceClient.*;

public class MdlpClient {
    private static Logger log = LoggerFactory.getLogger(MdlpClient.class);


    public static ResponseBpPageDto getBpMdlpPage(int currentPage, int pageSize){
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

    public static ResponseSgtinFilterPageDto postSgtinFilterRequest(Integer startFrom,
                                                                    Integer count,
                                                                    FilterForRequestDto filterForRequestDto){
        RequestSgtinFilterDto requestSgtinFilterDto = new RequestSgtinFilterDto(
                filterForRequestDto,
                count,
                startFrom,
                "NO_SORT"
        );

        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(requestSgtinFilterDto));

            log.info(objectMapper.writeValueAsString(requestSgtinFilterDto));

            String urlPath = properties.getProperty("host")
                    + "mdlp/api/reestr/"
                    + properties.getProperty("routeId")
                    + "/sgtin/filter";

            return objectMapper.readValue(
                    postGuaranteedRequestSending(urlPath, requestBody),
                    ResponseSgtinFilterPageDto.class);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static HierarchySsccMdlpDto getHierarchySsccRequest(SsccDto ssccDto){
        String urlPath = properties.getProperty("host")
                + "mdlp/api/sscc/"
                + ssccDto.getSscc()
                + "/hierarchy";

        try {
            HierarchySsccMdlpDto hierarchySsccMdlpDto = objectMapper.readValue(
                    getGuaranteedRequestSending(urlPath),
                    HierarchySsccMdlpDto.class);
            if (hierarchySsccMdlpDto.getUp() != null) {
                return hierarchySsccMdlpDto;
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String postGuaranteedRequestSending(String urlPath,
                                                      RequestBody requestBody) throws IOException {
        Request request = postMdlpProxyRequestWithAuthGetType(urlPath, requestBody);

        return guaranteedSending(request);
    }

    public static String getGuaranteedRequestSending(String urlPath) throws IOException {
        Request request = getMdlpProxyRequestWithAuthGetType(urlPath);

        return guaranteedSending(request);
    }

    private static String guaranteedSending(Request request) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String responseBody = null;

        Response response = okHttpClient.newCall(request).execute();
        while (!response.isSuccessful()){
            log.error(request.toString());
            log.error(request.headers().toString());
            if (!request.method().equals("GET"))
                log.error("Request body: " +  bodyToString(request.body()));
            log.error(response.toString());
            log.error(response.body().string());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            response = okHttpClient.newCall(request).execute();
        }
        responseBody = response.body().string();
        log.info(responseBody);
        response.close();

        return responseBody;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    public static HierarchySsccMdlpDto getMdlpHierarchy (String sscc) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String urlPath = properties.getProperty("host")
                + "mdlp/api/sscc/"
                + sscc
                + "/hierarchy";
        String str = getResponseBody(urlPath);

        HierarchySsccMdlpDto hierarchySsccMdlpDto = null;
        try {
            hierarchySsccMdlpDto =
                    objectMapper.readValue(str, HierarchySsccMdlpDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hierarchySsccMdlpDto.getUp() != null){
            log.info(hierarchySsccMdlpDto.toString());
            return  hierarchySsccMdlpDto;
        }
        else {
            log.info("Пустая иерархия на запросе sscc " + sscc);
            return null;
        }
    }

    public static PageDtoOfCodeTreeRootDto getPagedCodeTreeRootsByCode (String code) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/codetreeroots"
                + "?code.sgtinOrSscc=" + code;
        String str = getResponseBody(urlPath);

        log.info(str);

        return objectMapper
                .readValue(str, PageDtoOfCodeTreeRootDto.class);
    }
}
