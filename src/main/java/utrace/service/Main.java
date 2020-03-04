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

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("C:\\Users\\Vladimir\\IdeaProjects\\AnalyticsUtil\\src\\main\\java\\utrace\\service\\app.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Set<Event> events = new HashSet<>();

        Integer pageNum = 0;
        Integer totalPages;
        Integer totalElements;
        Integer totalNotifications = 0;
        Integer baseEvents = 0;

        PageDtoOfBriefedBusinessEventDto pagedEvents = getPagedEvents(pageNum);

        totalPages = pagedEvents.getPage().getTotalPages();
        totalElements = pagedEvents.getPage().getTotalElements();
        System.out.println("Страниц: " + totalPages + " , элементов: " + totalElements);

        for (; pageNum < totalPages; pageNum++) {
            pagedEvents = getPagedEvents(pageNum);

            for (EventDto eventDto : pagedEvents.getData()) {
                Event event = (Event) eventDto.fromDtoToEntity();
                System.out.println("\n\nОбрабатываю событие с id=" + event.getId() + " с типом " + event.getType());

                if (event.getType().contains("NOTIFICATION")){
                    totalNotifications++;
                    System.out.println("Событие с id=" + event.getId() + " является уведомлением");
                    continue;
                }

                if (event.getType().equals("OUTCOME_ACCEPT")
                || event.getType().equals("FOREIGN_IMPORT")){
                    System.out.println("Событие с id=" + event.getId() + " событием без аудитлога");
                    continue;
                }

                if (event.getType().equals("BASE")){
                    baseEvents++;
                    System.out.println("Событие с id=" + event.getId() + " является базовым");
                    continue;
                }

                System.out.println("Получаю статусы по событию с id=" + event.getId());

                PageDtoOfAuditRecordDto pageDtoOfAuditRecordDto = null;
                try {
                    pageDtoOfAuditRecordDto = getPagedAuditRecords(event);
                }
                catch (NullPointerException e){
                    System.out.println("Что-то пошло не так, аудитлог не обнаружен");
                    e.printStackTrace();
                }
                Set<EventStatus> eventStatuses = new HashSet<>(); //набор статусов и времени перехода по событию
                for (AuditRecordDto auditRecordDto : pageDtoOfAuditRecordDto.getData()) {
                    if (auditRecordDto.getChangedProperties().contains("status"))
                        eventStatuses.add((EventStatus) auditRecordDto.fromDtoToEntity());
                }

                event.setEventStatuses(eventStatuses);

                if (event.getStatus().equals("POSTED")
                        && !event.getRegulatorStatus().equals("QUEUE")
                        && !event.getRegulatorStatus().equals("NOT_REQUIRED")){
                    PageDtoOfBusinessEventMessageDto pageDtoOfBusinessEventMessageDto = getPagedEventMessages(event);



                    System.out.println("Число отправленных в МДЛП сообщений по событию с id=" + event.getId()
                    + " равно: " + pageDtoOfBusinessEventMessageDto.getEventMessageDtos().size());
                }

                events.add(event);

                System.out.println(event.getType());
                System.out.println("Статусы события:");
                for (EventStatus eventStatus : event.getEventStatuses()) {
                    System.out.println(eventStatus.getStatus());
                }
            }
            System.out.println(
                    "Всего в списке событий: " + events.size() + " из " + totalElements + " элементов."
                            + " Также присутствует " + totalNotifications + " уведомлений из МДЛП."
                            + " Базовых событий: " + baseEvents);
        }
    }

    static PageDtoOfBriefedBusinessEventDto getPagedEvents(Integer pageNum) throws IOException {
        String urlPath = properties.getProperty("host")
                + "events?&size=" + properties.getProperty("eventSizeReq")
                + "&page=" + pageNum;

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);
    }

    static PageDtoOfAuditRecordDto getPagedAuditRecords(Event event) throws IOException {

        String mappedEventType = MappingType.getAuditRecordTypeFromEventType(event.getType());

        String urlPath = properties.getProperty("host")
                + "audit/" + mappedEventType
                + "/" + event.getId();

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfAuditRecordDto.class);
    }

    static PageDtoOfBusinessEventMessageDto getPagedEventMessages (Event event) throws IOException {

        String urlPath = properties.getProperty("host")
                + "event-messages?"
                + "&businessEvent.id=" + event.getId()
                + "&direction=OUTCOME"
                + "&externalSystemId=8154639c-ab67-11e8-98d0-529269fb2178";

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBusinessEventMessageDto.class);
    }

    static Request getRequestWithAuthGetType(String urlPath){
        return new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", properties.getProperty("authorization"))
                .build();
    }

    static String getResponseBody(String urlPath) throws IOException {
        Request getRequest = getRequestWithAuthGetType(urlPath);

        Response getResponseReq = okHttpClient.newCall(getRequest).execute();

        ResponseBody getRespBody = getResponseReq.body();

        assert getRespBody != null;
        String str = getRespBody.string();

        getRespBody.close();
        return str;
    }
}
