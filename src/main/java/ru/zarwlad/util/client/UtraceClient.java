package ru.zarwlad.util.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageHistoryDto;
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

        return str;
    }

    public static PageDtoOfEventLineDto getPageEventLinesFromEvent (String eventId, int page) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/event-lines"
                + "?event.id=" + eventId
                + "&page=" + page;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfEventLineDto.class);
    }
}