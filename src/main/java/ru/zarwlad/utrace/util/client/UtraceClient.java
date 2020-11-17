package ru.zarwlad.utrace.util.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.*;
import ru.zarwlad.utrace.data.AuthData;
import ru.zarwlad.utrace.util.MappingEventTypeToAuditUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.zarwlad.utrace.util.client.ObjectMapperConfig.objectMapper;
import static ru.zarwlad.utrace.util.client.OkHttpConfig.okHttpClient;
import static ru.zarwlad.utrace.util.client.PropertiesConfig.properties;

public class UtraceClient {
    private static Logger log = LoggerFactory.getLogger(UtraceClient.class);

    @Deprecated
    public static PageDtoOfBriefedBusinessEventDto getPagedEvents(Integer pageNum) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "events?&size=" + properties.getProperty("eventSizeReq")
                + "&page=" + pageNum;

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);
    }

    public static PageDtoOfBriefedBusinessEventDto getPagedEventsByFilter(List<String> filter) throws IOException {
        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("coreApi"))
                .append("events");

        if (!filter.isEmpty()){
            urlPath.append("?");
            filter.forEach(urlPath::append);
        }

        String str = getResponseBody(urlPath.toString());

        return objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);
    }

    public static PageDtoOfAuditRecordDto getPagedAuditRecords(String type, String id) throws IOException {

        String mappedEventType = MappingEventTypeToAuditUrl.getAuditRecordTypeFromEventType(type);

        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "audit/" + mappedEventType
                + "/" + id;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfAuditRecordDto.class);
    }

    public static PageDtoOfBusinessEventMessageDto getPagedEventMessagesForMdlp (String id) throws IOException {

        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "event-messages?"
                + "&businessEvent.id=" + id
                + "&direction=OUTCOME"
                + "&externalSystemId=8154639c-ab67-11e8-98d0-529269fb2178";

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBusinessEventMessageDto.class);
    }

    public static EventDto getEventById (String id) throws IOException {

        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "events/"
                + id;

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, EventDto.class);
    }

    public static List<String> postProcessDefaultConvMsg(String id){
        String urlPath = properties.getProperty("host")
                + "default-converter/api/"
                + "convert-message-to-business-event"
                + "?messageId="
                + id;
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "");

            Request request = postRequestWithAuthGetType(urlPath, requestBody);

            Response response = okHttpClient.newCall(request).execute();

            assert response.body() != null;
            String str = response.body().string();

            return objectMapper.readValue(str, List.class);

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static List<MessageHistoryDto> getMessageHistoriesByMsgId(String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi1")
                + "message/" + id
                + "/message-histories";
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

    public static PageMessageDto getPagedMessagesByFilter (List<String> filter) throws IOException {

        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("journalApi2"))
                .append("message/paged");

        if (!filter.isEmpty()){
            urlPath.append("?");
            filter.forEach(urlPath::append);
        }

        String str = getResponseBody(urlPath.toString());

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
        Request getRequest;

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
            assert str != null;
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
            assert str != null;
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
            assert str != null;
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

            assert response.body() != null;
            String str = response.body().string();

            return objectMapper.readValue(str, UnitUnpackEventDto.class);

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static EventLineDto postUnitUnpackEventLine (List<EventLineDto> eventLinePostDtos, UnitUnpackEventDto event){
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(eventLinePostDtos));

            String urlPath = properties.getProperty("host")
                    + "api/2.0/events/unit-unpack/"
                    + event.getId()
                    + "/event-lines";

            Request request = postRequestWithAuthGetType(urlPath, requestBody);

            Response response = okHttpClient.newCall(request).execute();

            assert response.body() != null;
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

            assert response.body() != null;
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

    public static EventDto postEventById(String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/events/"
                + id
                + "/post";

        Request request = postRequestWithAuthGetType(urlPath, null);
        Response response = okHttpClient.newCall(request).execute();
        log.info("Thread: {}, eventId: {}, status: {}, body: {}",
                Thread.currentThread().getName(),
                id,
                response.code(),
                response.body().string());
        return objectMapper.readValue(response.body().string(), EventDto.class);
    }

    public static String postEventByIdReturnString(String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/events/"
                + id
                + "/post";

        RequestBody rb = RequestBody.create(MediaType.parse("application/json"), "");
        Request request = postRequestWithAuthGetType(urlPath, rb);
        Response response = okHttpClient.newCall(request).execute();

        String str = response.body().string();
        log.info("Thread: {}, eventId: {}, status: {}, body: {}",
                Thread.currentThread().getName(),
                id,
                response.code(),
                str);
        return str;
    }

    public static MessageHistoryDto postErrorForMessage(String messageId,
                                                        String messageBundleUrl,
                                                        String externalSystemResponse,
                                                        String errorCode,
                                                        List<String> errorArgs){
        StringBuilder body = new StringBuilder();
        body.append("[");
        body.append("{");
        body.append("\"errorArgs\": [");
        errorArgs.forEach(x -> {
            body.append("\"");
            body.append(x);
            body.append("\"");
            if (errorArgs.indexOf(x) != errorArgs.size() - 1)
                body.append(",");
        });
        body.append("]");
        body.append(",");
        body.append("\"errorCode\": \"");
        body.append(errorCode);
        body.append("\"");
        body.append("}");
        body.append("]");

        System.out.println(body);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body.toString());

        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("journalApi1"))
                .append("message-history/save-error")
                .append("?")
                .append("externalSystemResponse=")
                .append(externalSystemResponse)
                .append("&messageBundleUrl=")
                .append(messageBundleUrl)
                .append("&messageId=")
                .append(messageId);
        Request request = postRequestWithAuthGetType(urlPath.toString(), requestBody);

        try {
            Response response = okHttpClient.newCall(request).execute();
            String str = response.body().string();
            return objectMapper.readValue(str, MessageHistoryDto.class);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.error(urlPath.toString());
            log.error(requestBody.toString());

            return null;
        }
    }

    public static PageDtoOfEventLineDto getPagedEventLinesByFilter(List<String> filter) throws IOException {
        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("coreApi"))
                .append("event-lines");

        if (!filter.isEmpty()){
            urlPath.append("?");
            filter.forEach(urlPath::append);
        }

        String str = getResponseBody(urlPath.toString());

        return objectMapper
                .readValue(str, PageDtoOfEventLineDto.class);
    }

    public static PageBatchInfoDto getPagedBatchInfoByFilter(List<String> filter) throws IOException {
        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("coreApi"))
                .append("event-batch-info");

        if (!filter.isEmpty()){
            urlPath.append("?");
            filter.forEach(urlPath::append);
        }

        String str = getResponseBody(urlPath.toString());

        return objectMapper
                .readValue(str, PageBatchInfoDto.class);
    }

    public static BatchInfoDto postBatchInfo(BatchInfoDto batchInfoDto) throws IOException {
        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("coreApi"))
                .append("event-batch-info");

        RequestBody rb = RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(batchInfoDto));
        Request request = postRequestWithAuthGetType(urlPath.toString(), rb);
        Response response = okHttpClient.newCall(request).execute();

        String str = response.body().string();
        return objectMapper.readValue(str, BatchInfoDto.class);
    }

    public static PageTradeItemBriefDto getPagedTradeItemsByFilter(List<String> filter) throws IOException {
        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("coreApi"))
                .append("trade-items");

        if (!filter.isEmpty()){
            urlPath.append("?");
            filter.forEach(urlPath::append);
        }

        String str = getResponseBody(urlPath.toString());

        return objectMapper
                .readValue(str, PageTradeItemBriefDto.class);
    }

    public static List<EventLineDto> postLinesForMoveOwnerNotif(List<EventLineDto> eventLineDtos, String eventId) throws IOException {
        StringBuilder urlPath = new StringBuilder();
        urlPath.append(properties.getProperty("host"))
                .append(properties.getProperty("coreApi"))
                .append("events/move-owner-notification/")
                .append(eventId + "/")
                .append("event-lines");

        log.info("Посылаю тело запроса {} ", objectMapper.writeValueAsString(eventLineDtos));
        RequestBody rb = RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(eventLineDtos));
        Request request = postRequestWithAuthGetType(urlPath.toString(), rb);
        Response response = okHttpClient.newCall(request).execute();

        String str = response.body().string();
        EventLineDto[] answers = objectMapper.readValue(str, EventLineDto[].class);
        return Arrays.asList(answers);
    }

    public static EventLineDto postLinesForMoveOwnerNotif(EventLineDto eventLineDto, String eventId) throws IOException {
        List<EventLineDto> lines = new ArrayList<>();
        lines.add(eventLineDto);

        List<EventLineDto> newLines = UtraceClient.postLinesForMoveOwnerNotif(lines, eventId);

        return newLines.get(0);
    }

    public static void validateEventById(String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/events/"
                + id
                + "/validate";

        Request request = postRequestWithAuthGetType(urlPath, RequestBody.create(MediaType.parse("application/json"), ""));
        Response response = okHttpClient.newCall(request).execute();
        log.info("Thread: {}, eventId: {}, status: {}, body: {}",
                Thread.currentThread().getName(),
                id,
                response.code(),
                response.body().string());
    }

    public static void createFromDraftEventById(String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/events/"
                + id
                + "/create-from-draft";

        Request request = putRequestWithAuthGetType(urlPath, RequestBody.create(MediaType.parse("application/json"), ""));
        Response response = okHttpClient.newCall(request).execute();
        log.info("Thread: {}, eventId: {}, status: {}, body: {}",
                Thread.currentThread().getName(),
                id,
                response.code(),
                response.body().string());
    }


}
