package utrace.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import utrace.dto.*;
import utrace.entities.Event;
import utrace.entities.EventStatus;
import utrace.util.MappingType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) throws IOException {
        Set<Event> events = new HashSet<>();

        Integer pageNum = 0;
        Integer totalPages;
        Integer totalElements;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(50_000, TimeUnit.MILLISECONDS)
                .build();

        Properties properties = new Properties();
        properties.load(new FileReader(new File("C:\\Users\\Vladimir\\IdeaProjects\\AnalyticsUtil\\src\\main\\java\\utrace\\service\\app.properties")));

        PageDtoOfBriefedBusinessEventDto pagedEvents = getPagedEvents(okHttpClient, properties, pageNum);

        totalPages = pagedEvents.getPage().getTotalPages();
        totalElements = pagedEvents.getPage().getTotalElements();
        System.out.println("Страниц: " + totalPages + " , элементов: " + totalElements);

        for (; pageNum < totalPages; pageNum++) {
            pagedEvents = getPagedEvents(okHttpClient, properties, pageNum);

            for (EventDto eventDto : pagedEvents.getData()) {
                Event event = (Event) eventDto.fromDtoToEntity();

                PageDtoOfAuditRecordDto pageDtoOfAuditRecordDto = getPagedAuditRecords(okHttpClient, properties, event);

                Set<EventStatus> eventStatuses = new HashSet<>();
                for (AuditRecordDto auditRecordDto : pageDtoOfAuditRecordDto.getData()) {
                    if (auditRecordDto.getChangedProperties().contains("status"))
                        eventStatuses.add((EventStatus) auditRecordDto.fromDtoToEntity());
                }

                event.setEventStatuses(eventStatuses);

                events.add(event);
                System.out.println(event.getType());
                System.out.println("Статусы события:");
                for (EventStatus eventStatus : event.getEventStatuses()) {
                    System.out.println(eventStatus.getStatus());
                }
            }
            System.out.println("Всего в списке событий: " + events.size() + " из " + totalElements + " элементов");
        }
    }

    static PageDtoOfBriefedBusinessEventDto getPagedEvents(OkHttpClient okHttpClient, Properties properties, Integer pageNum) throws IOException {
        String urlPath = properties.getProperty("host")
                + "events?&size=" + properties.getProperty("eventSizeReq")
                + "&page=" + pageNum;

        String str = getResponseBody(okHttpClient, urlPath, properties, objectMapper);

        return objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);
    }

    static PageDtoOfAuditRecordDto getPagedAuditRecords(OkHttpClient okHttpClient, Properties properties, Event event) throws IOException {

        String mappedEventType = MappingType.getAuditRecordTypeFromEventType(event.getType());

        String urlPath = properties.getProperty("host")
                + "audit/" + mappedEventType
                + "/" + event.getId();

        String str = getResponseBody(okHttpClient, urlPath, properties, objectMapper);

        return objectMapper
                .readValue(str, PageDtoOfAuditRecordDto.class);
    }

    static PageDtoOfBusinessEventMessageDto getPagedEventMessages (OkHttpClient okHttpClient, Properties properties, Event event) throws IOException {

        String urlPath = properties.getProperty("host")
                + "/2.0/event-messages?"
                + "&businessEvent.id=" + event.getId()
                + "&direction=OUTCOME"
                + "&externalSystemId=8154639c-ab67-11e8-98d0-529269fb2178";

        String str = getResponseBody(okHttpClient, urlPath, properties, objectMapper);

        return objectMapper
                .readValue(str, PageDtoOfBusinessEventMessageDto.class);
    }

    static Request getRequestWithAuthGetType(String urlPath, Properties properties){
        return new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", properties.getProperty("authorization"))
                .build();
    }

    static String getResponseBody(OkHttpClient okHttpClient,
                                  String urlPath,
                                  Properties properties,
                                  ObjectMapper objectMapper) throws IOException {
        Request getRequest = getRequestWithAuthGetType(urlPath, properties);

        Response getResponseReq = okHttpClient.newCall(getRequest).execute();

        ResponseBody getRespBody = getResponseReq.body();

        assert getRespBody != null;
        String str = getRespBody.string();

        getRespBody.close();
        return str;
    }
}
