package ru.zarwlad.utrace.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.data.AuthData;
import ru.zarwlad.utrace.data.EventData;
import ru.zarwlad.utrace.modelDtoMapper.EventModelMapper;
import ru.zarwlad.utrace.modelDtoMapper.EventStatusMapper;
import ru.zarwlad.utrace.utraceDto.*;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.model.MessageHistory;
import ru.zarwlad.utrace.util.MappingType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DownloadEventsService {
    private static Logger log = LoggerFactory.getLogger(DownloadEventsService.class);

    static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50_000, TimeUnit.MILLISECONDS)
            .build();

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static void startDownload() throws IOException {
        Set<Event> events = new HashSet<>();

        Integer pageNum = 0;
        Integer totalPages;
        Integer totalElements;

        PageDtoOfBriefedBusinessEventDto pagedEvents = getPagedEvents(pageNum);

        totalPages = pagedEvents.getPage().getTotalPages();
        totalElements = pagedEvents.getPage().getTotalElements();
        log.info("Страниц: {}, элементов: {}", totalPages, totalElements);

        for (; pageNum < totalPages; pageNum++) {

            /*
             * Получаем список событий на странице
             */

            pagedEvents = getPagedEvents(pageNum);

            /*
             * Обрабатываем каждое событие со страницы
             */

            /*
             * В ходе рефакторинга нужно: покрыть случай, когда у события нет аудитлога
             * Скачивать все события без разбора. Логику расчета статистики перенести в соответствующий сервис
             * Скачивать все изменения статусов без разбора.
             * Если статус не QUEUE и не NOT_REQUIRED - запрашивать сообщения
             * Перенести преобразования dto -> entity в маппер
             */

            for (EventDto eventDto : pagedEvents.getData()) {
                //тут цель - создать событие, у которого будет само событие, его статусы и сообщения в мдлп
                log.info("Обрабатываю событие с id= {}, тип {}, статус {}",
                        eventDto.getId(),
                        eventDto.getType(),
                        eventDto.getStatus());

                Event event = EventModelMapper.fromDtoToEntity(eventDto);

                //TODO: вынести поля messages, statuses - из event
                /*
                * Получаем статусы события, проходясь по аудитлогу
                * Сохраняем только те изменения, которые относятся к изменению статуса
                 */

                log.info("Получаю статусы по событию с id= {}",
                        event.getId());

                PageDtoOfAuditRecordDto pageDtoOfAuditRecordDto = getPagedAuditRecords(event);
                log.info("Загружено {} записей из аудитлога по событию = {}",
                        pageDtoOfAuditRecordDto.getData().size(),
                        event.getId());

                Set<EventStatus> eventStatuses = new HashSet<>();
                for (AuditRecordDto auditRecordDto : pageDtoOfAuditRecordDto.getData()) {
                    if (auditRecordDto.getChangedProperties().contains("status"))
                        eventStatuses.add(EventStatusMapper.fromDtoToEntity(auditRecordDto));
                }
                event.setEventStatuses(eventStatuses);

                if (!event.getRegulatorStatus().equals("QUEUE")
                        && !event.getRegulatorStatus().equals("NOT_REQUIRED")){
                    PageDtoOfBusinessEventMessageDto pageDtoOfBusinessEventMessageDto = getPagedEventMessages(event);

                    log.info("Число отправленных в МДЛП сообщений по событию с id= {}  равно: {}",
                            event.getId(),
                            pageDtoOfBusinessEventMessageDto.getEventMessageDtos().size());

                    /*
                     * Получаем сообщение по id из eventMessage.messageId
                     */

                    Set<Message> messages = new HashSet<>();
                    for (EventMessageDto eventMessageDto : pageDtoOfBusinessEventMessageDto.getEventMessageDtos()) {
                        log.info("Получаю сообщение с id= {}", eventMessageDto.getMessageId());

                        PageMessageDto pageMessageDto = getPagedSingleMessageById(eventMessageDto.getMessageId());
                        try {
                            pageMessageDto.getMessageDtos().isEmpty();
                        }
                        catch (NullPointerException e){
                            log.warn("Необходимо использовать старое API для загрузки, {}",
                                    e.toString());

                            pageMessageDto = getOldPagedMessageDtoById(eventMessageDto.getMessageId());
                        }

                        Message message = null;
                                try {
                                    message = pageMessageDto.getMessageDtos().get(0).fromDtoToEntity();
                                }
                                catch (IndexOutOfBoundsException e){
                                    log.warn("По событию {} есть запись в eventMessage, но нет сообщения!", event.getId());
                                }

                        /*
                        * Получаем историю по обработке сообщения и записываем в Message
                         */
                        log.info("Получаю историю обработки сообщения с id= {}",
                                eventMessageDto.getMessageId());

                        if (message != null) {
                            List<MessageHistoryDto> messageHistoryDtos = getMessageHistoriesByMsg(message);
                            Set<MessageHistory> messageHistories = new HashSet<>();
                            for (MessageHistoryDto messageHistoryDto : messageHistoryDtos) {
                                messageHistories.add((MessageHistory) messageHistoryDto.fromDtoToEntity());
                            }
                            message.setMessageHistories(messageHistories);
                            messages.add(message);
                        }
                    }
                    event.setMessages(messages);
                }
                events.add(event);
            }
            log.info("Всего в списке событий {} из {} элементов",
                    events.size(),
                    totalElements);
        }

        EventData eventData = EventData.getInstance();
        eventData.setEvents(events);
    }

    static PageDtoOfBriefedBusinessEventDto getPagedEvents(Integer pageNum) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "events/register?&size=" + properties.getProperty("eventSizeReq")
                + "&page=" + pageNum;

        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfBriefedBusinessEventDto.class);
    }

    static PageDtoOfAuditRecordDto getPagedAuditRecords(Event event) throws IOException {

        String mappedEventType = MappingType.getAuditRecordTypeFromEventType(event.getType());

        String urlPath = properties.getProperty("host")
                + properties.getProperty("coreApi")
                + "audit/" + mappedEventType
                + "/" + event.getId();
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfAuditRecordDto.class);
    }

    static PageDtoOfBusinessEventMessageDto getPagedEventMessages (Event event) throws IOException {

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

    static List<MessageHistoryDto> getMessageHistoriesByMsg(Message message) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi1")
                + "message/" + message.getId() + "/message-histories";
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, new TypeReference<List<MessageHistoryDto>>(){});
    }

    static PageMessageDto getPagedSingleMessageById (String id) throws IOException {

        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi2")
                + "message/paged?id=" + id;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageMessageDto.class);
    }

    static PageMessageDto getOldPagedMessageDtoById (String id) throws IOException {
        String urlPath = properties.getProperty("host")
                + properties.getProperty("journalApi1")
                + "message/paged?Message.id=" + id;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageMessageDto.class);
    }

    static Request getRequestWithAuthGetType(String urlPath){
        return new Request.Builder()
                .get()
                .url(urlPath)
                .addHeader("authorization", AuthData.getInstance().getAuth().getAccessToken())
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