package ru.zarwlad.utrace.service;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.*;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfAuditRecordDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfBusinessEventMessageDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.dao.EventStatusDao;
import ru.zarwlad.utrace.dao.MessageDao;
import ru.zarwlad.utrace.dao.MessageHistoryDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.model.MessageHistory;
import ru.zarwlad.utrace.modelDtoMapper.CommonMapper;
import ru.zarwlad.utrace.modelDtoMapper.EventStatusMapper;
import ru.zarwlad.utrace.modelDtoMapper.MessageHistoryMapper;
import ru.zarwlad.utrace.modelDtoMapper.MessageModelMapper;

import java.io.IOException;
import java.util.*;

import static ru.zarwlad.util.client.UtraceClient.*;

@Getter
@Setter
public class MultiThreadEventDownloader implements Runnable {
    private static Logger log = LoggerFactory.getLogger(MultiThreadEventDownloader.class);

    private List<String> ids;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String id : ids) {
            builder.append(id).append(" ");
        }
        return "MultiThreadEventDownloader{" +
                "ids=" + builder +
                '}';
    }

    @SneakyThrows
    @Override
    public void run() {
        download(ids);
    }


    private static void download(List<String> ids) throws IOException {
        log.info("Thread name {}, batch of id from {} to {}",
                Thread.currentThread().getName(),
                ids.get(0),
                ids.get(ids.size() - 1));

        for (String id : ids) {
            EventDto eventDto = UtraceClient.getEventById(id);

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

    private static Set<AuditRecordDto> downloadAuditRecords(EventDto eventDto) throws IOException {
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
