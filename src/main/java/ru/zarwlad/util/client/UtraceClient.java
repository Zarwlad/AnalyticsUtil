package ru.zarwlad.util.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.EventLinePostDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageHistoryDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.UnitUnpackEventDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.UnitUnpackEventPostDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.*;
import ru.zarwlad.utrace.data.AuthData;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.util.MappingEventTypeToAuditUrl;

import java.io.IOException;
import java.util.List;

import static ru.zarwlad.util.client.ObjectMapperConfig.objectMapper;
import static ru.zarwlad.util.client.OkHttpConfig.okHttpClient;
import static ru.zarwlad.util.client.PropertiesConfig.properties;

public class UtraceClient {
    private static Logger log = LoggerFactory.getLogger(UtraceClient.class);

    public static PageDtoOfBriefedBusinessEventDto getPagedEvents(Integer pageNum) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "events/register?&size=" + properties.getProperty("eventSizeReq")
                + "&page=" + pageNum;

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);
    }

    public static PageDtoOfAuditRecordDto getPagedAuditRecords(Event event) throws IOException {

        String mappedEventType = MappingEventTypeToAuditUrl.getAuditRecordTypeFromEventType(event.getType());

        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "audit/" + mappedEventType
                + "/" + event.getId();
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfAuditRecordDto.class);
    }

    public static PageDtoOfBusinessEventMessageDto getPagedEventMessages (Event event) throws IOException {

        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "event-messages?"
                + "&businessEvent.id=" + event.getId()
                + "&direction=OUTCOME"
                + "&externalSystemId=8154639c-ab67-11e8-98d0-529269fb2178";

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBusinessEventMessageDto.class);
    }

    public static List<MessageHistoryDto> getMessageHistoriesByMsg(Message message) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi1")
                + "message/" + message.getId() + "/message-histories";
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, new TypeReference<List<MessageHistoryDto>>(){});
    }

    public static PageMessageDto getPagedSingleMessageById (String id) throws IOException {

        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi2")
                + "message/paged?id=" + id;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageMessageDto.class);
    }

    public static PageMessageDto getOldPagedMessageDtoById (String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi1")
                + "message/paged?Message.id=" + id;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageMessageDto.class);
    }

    public static Request getRequestWithAuthGetType(String urlPath){
        return new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", AuthData.getInstance().getAuth().getAccessToken())
                .build();
    }

    public static Request getMdlpProxyRequestWithAuthGetType(String urlPath){
        return new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", AuthData.getInstance().getAuth().getAccessToken())
                .addHeader("Route", properties.getProperty("mdlpRoute"))
                .build();
    }

    public static Request postMdlpProxyRequestWithAuthGetType(String urlPath, RequestBody requestBody){
        return new Request.Builder()
                .post(requestBody)
                .url(urlPath)
                .addHeader("authorization", AuthData.getInstance().getAuth().getAccessToken())
                .addHeader("Route", properties.getProperty("mdlpRoute"))
                .build();
    }

    public static Request postRequestWithAuthGetType(String urlPath, RequestBody requestBody){
        return new Request.Builder()
                .post(requestBody)
                .url(urlPath)
                .addHeader("authorization", AuthData.getInstance().getAuth().getAccessToken())
                .build();
    }

    public static Request putRequestWithAuthGetType(String urlPath, RequestBody requestBody){
        return new Request.Builder()
                .put(requestBody)
                .url(urlPath)
                .addHeader("authorization", AuthData.getInstance().getAuth().getAccessToken())
                .build();
    }

    public static String getResponseBody(String urlPath) throws IOException {
        Request getRequest = null;

        if (!urlPath.contains("/mdlp/api")) {
            getRequest = getRequestWithAuthGetType(urlPath);
        }
        else {
            getRequest = getMdlpProxyRequestWithAuthGetType(urlPath);
        }
        Response getResponseReq = okHttpClient.newCall(getRequest).execute();
        ResponseBody getRespBody = getResponseReq.body();

        assert getRespBody != null;

        String str = getRespBody.string();
        getRespBody.close();

        log.info("getRespBody: {}, getResponseReq.code: {}", str, getResponseReq.code());
        return str;
    }

    public static PageDtoOfEventLineDto getPageEventLinesFromEvent (String eventId, int page) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/event-lines"
                + "?event.id=" + eventId
                + "&parent"
                + "&page=" + page
                + "&size=" + 2000;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfEventLineDto.class);
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

    public static PageDtoOfCodeTreeRootDto getPagedCodeTreeRootsByFilter (String filter, Integer page, Integer size) {

        String urlPath = properties.getProperty("host")
                + "api/2.0/codetreeroots"
                + "?page=" + page
                + "&size=" + size;

        if (!"NO".equals(filter))
            urlPath = urlPath + "&" + filter;

        String str = null;
        try {
            str = getResponseBody(urlPath);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        log.info(str);

        try {
            return objectMapper
                    .readValue(str, PageDtoOfCodeTreeRootDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static PageCodeDto getPagedCodesByFilter (String filter, Integer page, Integer size) {

        String urlPath = properties.getProperty("host")
                + "api/2.0/codes"
                + "?page=" + page
                + "&size=" + size;

        if (!"NO".equals(filter))
            urlPath = urlPath + "&" + filter;

        String str = null;
        try {
            str = getResponseBody(urlPath);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        log.info(str);

        try {
            return objectMapper
                    .readValue(str, PageCodeDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static PageBatchSgtinQuantityDto getPagedBatchesInSscc (String ssccValue, Integer page, Integer size){
        String urlPath = properties.getProperty("host")
                + "api/2.0/codes/sscc/"
                + ssccValue + "/batches"
                + "?page=" + page
                + "&size=" + size;
        String str = null;
        try {
            str = getResponseBody(urlPath);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        log.info(str);

        try {
            return objectMapper
                    .readValue(str, PageBatchSgtinQuantityDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static UnitUnpackEventDto postUnitUnpackEvent (UnitUnpackEventPostDto unitUnpackEventPostDto){
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(unitUnpackEventPostDto));

            String urlPath = properties.getProperty("host")
                    + "api/2.0/events/unit-unpack";

            Request request = postRequestWithAuthGetType(urlPath, requestBody);

            Response response = okHttpClient.newCall(request).execute();

            String str = response.body().string();

            return objectMapper.readValue(str, UnitUnpackEventDto.class);

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static EventLineDto postUnitUnpackEventLine (List<EventLinePostDto> eventLinePostDtos, UnitUnpackEventDto event){
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(eventLinePostDtos));

            String urlPath = properties.getProperty("host")
                    + "api/2.0/events/unit-unpack/"
                    + event.getId()
                    + "/event-lines";

            Request request = postRequestWithAuthGetType(urlPath, requestBody);

            Response response = okHttpClient.newCall(request).execute();

            String str = response.body().string();

            return objectMapper.readValue(str, EventLineDto.class);

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static UnitUnpackEventDto putUnitUnpackCreatedStatus (UnitUnpackEventDto unitUnpackEventDto){
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    "");

            String urlPath = properties.getProperty("host")
                    + "api/2.0/events/unit-unpack/"
                    + unitUnpackEventDto.getId()
                    + "/status/create";

            Request request = putRequestWithAuthGetType(urlPath, requestBody);

            Response response = okHttpClient.newCall(request).execute();

            String str = response.body().string();

            return objectMapper.readValue(str, UnitUnpackEventDto.class);

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static void getMsgResetErrors (String id) throws IOException {

        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi1")
                + "message/" + id
                + "/reset-errors";
        String str = getResponseBody(urlPath);

        log.info("{}", urlPath);
    }
}
