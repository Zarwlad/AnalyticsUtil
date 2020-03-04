package utrace.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import utrace.dto.AuditRecordDto;
import utrace.dto.EventDto;
import utrace.dto.PageDtoOfAuditRecordDto;
import utrace.dto.PageDtoOfBriefedBusinessEventDto;
import utrace.entities.Event;
import utrace.entities.EventStatus;
import utrace.util.MappingType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Set<Event> events = new HashSet<>();

        Integer pageNum = 0;
        Integer totalPages = -1;
        Integer totalElements = -1;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(50_000, TimeUnit.MILLISECONDS)
                .build();

        Properties properties = new Properties();
        properties.load(new FileReader(new File("C:\\Users\\Vladimir\\IdeaProjects\\AnalyticsUtil\\src\\main\\java\\utrace\\service\\app.properties")));

        PageDtoOfBriefedBusinessEventDto pagedEvents = getPagedEvents(okHttpClient, properties, pageNum);

        if (pagedEvents.getPage().getTotalPages() != 1){
            totalPages = pagedEvents.getPage().getTotalPages();
            totalElements = pagedEvents.getPage().getTotalElements();
        }

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
        Request getEvent = new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", properties.getProperty("authorization"))
                .build();

        Response getEventResp = okHttpClient.newCall(getEvent).execute();

        ResponseBody getEventRespBody = getEventResp.body();

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String str = getEventRespBody.string();

        PageDtoOfBriefedBusinessEventDto pageDtoOfBriefedBusinessEventDto = objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);

        getEventRespBody.close();
        return pageDtoOfBriefedBusinessEventDto;
    }

    static PageDtoOfAuditRecordDto getPagedAuditRecords(OkHttpClient okHttpClient, Properties properties, Event event) throws IOException {

        String mappedEventType = MappingType.getAuditRecordTypeFromEventType(event.getType());

        String urlPath = properties.getProperty("host")
                + "audit/" + mappedEventType
                + "/" + event.getId();

        Request getStatuses = new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", properties.getProperty("authorization"))
                .build();

        Response getStatusesResp = okHttpClient.newCall(getStatuses).execute();

        ResponseBody getStatusesRespBody = getStatusesResp.body();

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String str = getStatusesRespBody.string();

        PageDtoOfAuditRecordDto pageDtoOfAuditRecordDto = objectMapper
                .readValue(str, PageDtoOfAuditRecordDto.class);
        getStatusesRespBody.close();
        return pageDtoOfAuditRecordDto;
    }
}
