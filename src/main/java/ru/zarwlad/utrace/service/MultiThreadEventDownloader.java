package ru.zarwlad.utrace.service;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfAuditRecordDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfBusinessEventMessageDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.utrace.util.client.UtraceClient;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.dao.EventStatusDao;
import ru.zarwlad.utrace.dao.MessageDao;
import ru.zarwlad.utrace.dao.MessageHistoryDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.model.MessageHistory;
import ru.zarwlad.utrace.modelDtoMapper.CommonMapper;

import java.io.IOException;
import java.util.*;

import static ru.zarwlad.utrace.util.client.UtraceClient.*;

@Getter
@Setter
public class MultiThreadEventDownloader implements Runnable {
    private static Logger log = LoggerFactory.getLogger(MultiThreadEventDownloader.class);

    private List<String> ids;
    private List<EventDto> eventDtos;

    @SneakyThrows
    @Override
    public void run() {
        download(ids);

//        for (EventDto eventDto : eventDtos) {
//            Set<AuditRecordDto> recordDtos = downloadAuditRecords(eventDto);
//            EventDao eventDao = new EventDao(DbManager.getSessionFactory());
//            Event event = eventDao.readById(UUID.fromString(eventDto.getId()));
//
//            for (AuditRecordDto recordDto : recordDtos) {
//                EventStatus eventStatus = EventStatusMapper.fromDtoToEntity(recordDto);
//                eventStatus.setEvent(event);
//
//                EventStatusDao eventStatusDao = new EventStatusDao(DbManager.getSessionFactory());
//                eventStatusDao.create(eventStatus);
//            }
//        }

    }


    private static void download(List<String> ids) throws IOException {
        log.info("Thread name {}, batch of id from {} to {}",
                Thread.currentThread().getName(),
                ids.get(0),
                ids.get(ids.size() - 1));

        for (String id : ids) {

            Event e = new EventDao(DbManager.getSessionFactory()).readById(UUID.fromString(id));
            if (e != null){
                log.info("Thread {}, Событие с id = {} уже есть в базе", Thread.currentThread().getName(), id);
                continue;
            }

            EventDto eventDto = UtraceClient.getEventById(id.trim());

            Set<AuditRecordDto> auditRecordDtos = downloadAuditRecords(eventDto);

            Set<MessageDto> messageDtos = new HashSet<>();
            Map<MessageHistoryDto, MessageDto> messageHistoryMap = new HashMap<>();
            /*
            get event messages
             */
            if (!eventDto.getRegulatorStatus().equals("QUEUE")
                    && !eventDto.getRegulatorStatus().equals("NOT_REQUIRED")) {
                PageDtoOfBusinessEventMessageDto pageDtoOfBusinessEventMessageDto =
                        getPagedEventMessagesForMdlp(eventDto.getId());

                log.info("Thread {}, Число отправленных в МДЛП сообщений по событию с id= {}  равно: {}",
                        Thread.currentThread().getName(),
                        eventDto.getId(),
                        pageDtoOfBusinessEventMessageDto.getEventMessageDtos().size());

                messageDtos =
                        downloadMsgByEventMessages(pageDtoOfBusinessEventMessageDto.getEventMessageDtos());

                for (MessageDto messageDto : messageDtos) {
                    Map<MessageHistoryDto, MessageDto> h = downloadMessageHistoriesByMsg(messageDto);

                    for (Map.Entry<MessageHistoryDto, MessageDto> entry : h.entrySet()) {
                        entry.setValue(messageDto);
                    }

                    messageHistoryMap.putAll(h);
                }
            }

            Event event = CommonMapper.mapEventDtosToEntities(
                    eventDto,
                    auditRecordDtos,
                    messageDtos,
                    messageHistoryMap);

            saveToDb(event);
        }
    }

    public static Set<AuditRecordDto> downloadAuditRecords(EventDto eventDto) throws IOException {
        /*
         Download Audit records
        */

        PageDtoOfAuditRecordDto pageDtoOfAuditRecordDto = getPagedAuditRecords(eventDto.getType(), eventDto.getId());
        log.info("Thread {}, Загружено {} записей из аудитлога по событию = {}",
                Thread.currentThread().getName(),
                pageDtoOfAuditRecordDto.getData().size(),
                eventDto.getId());

            /*
            purification of changes
             */
        Set<AuditRecordDto> auditRecordDtos = new HashSet<>();
        for (AuditRecordDto auditRecordDto : pageDtoOfAuditRecordDto.getData()) {
            if (auditRecordDto.getChangedProperties().contains("status"))
                auditRecordDtos.add(auditRecordDto);
        }

        return auditRecordDtos;
    }

    private static Set<MessageDto> downloadMsgByEventMessages(List<EventMessageDto> eventMessageDtos) throws IOException {
        Set<MessageDto> messageDtos = new HashSet<>();

        for (EventMessageDto eventMessageDto : eventMessageDtos) {
            log.info("Thread {}, Получаю сообщение с id= {}",
                    Thread.currentThread().getName(),
                    eventMessageDto.getMessageId());

            PageMessageDto pageMessageDto = getPagedSingleMessageById(eventMessageDto.getMessageId());
            try {
                boolean b = pageMessageDto.getMessageDtos().isEmpty();
            } catch (NullPointerException e) {
                log.warn("Thread {}, Необходимо использовать старое API для загрузки, {}",
                        Thread.currentThread().getName(),
                        e.toString());
                pageMessageDto = getOldPagedMessageDtoById(eventMessageDto.getMessageId());
            }

            messageDtos.addAll(pageMessageDto.getMessageDtos());
        }
        return messageDtos;
    }

    private static Map<MessageHistoryDto, MessageDto> downloadMessageHistoriesByMsg (MessageDto messageDto) throws IOException {
        log.info("Thread {}, Получаю историю обработки сообщений с id= {}",
                Thread.currentThread().getName(),
                messageDto.getId());

        Map<MessageHistoryDto, MessageDto> messageHistoryMap = new HashMap<>();
        List<MessageHistoryDto> messageHistoryDtos = getMessageHistoriesByMsgId(messageDto.getId());
        for (MessageHistoryDto historyDto : messageHistoryDtos) {
            try {
                if (historyDto.getStatus() == null){
                    historyDto.setStatus("UPLOADED");
                }
            }
            catch (NullPointerException e){
                log.error("Thread {}, {}, historyDto.getId = {}, messageDto.getId = {}",
                        Thread.currentThread().getName(),
                        e.getLocalizedMessage(),
                        historyDto.getId(),
                        messageDto.getId());
            }

            messageHistoryMap.put(historyDto, null);
        }

        return messageHistoryMap;
    }

    private static void saveToDb (Event event){

        EventDao eventDao = new EventDao(DbManager.getSessionFactory());
        eventDao.create(event);

        for (EventStatus eventStatus : event.getEventStatuses()) {
            EventStatusDao eventStatusDao = new EventStatusDao(DbManager.getSessionFactory());
            eventStatusDao.create(eventStatus);
        }

        for (Message message : event.getMessages()) {
            MessageDao messageDao = new MessageDao(DbManager.getSessionFactory());
            messageDao.create(message);

            for (MessageHistory messageHistory : message.getMessageHistories()) {
                MessageHistoryDao messageHistoryDao = new MessageHistoryDao(DbManager.getSessionFactory());
                messageHistoryDao.create(messageHistory);
            }
        }
    }

}
