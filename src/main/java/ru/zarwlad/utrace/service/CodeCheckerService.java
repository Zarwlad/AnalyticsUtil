package ru.zarwlad.utrace.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.Documents;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.HierarchyInfoDto;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.SgtinInfo;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.SsccInfo;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.BatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.TradeItemBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageBatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageTradeItemBriefDto;
import ru.zarwlad.utrace.util.client.PropertiesConfig;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CodeCheckerService implements Runnable {
    private static String eventId;
    private static final Logger log = LoggerFactory.getLogger(CodeCheckerService.class);
    private static int sgtinCounter = 0;
    private static List<TradeItemBriefDto> tradeItemBriefDtos = new ArrayList<>();
    private static List<BatchInfoDto> batchBriefDtos = new ArrayList<>();
    private static List<String> uploadedCodes = new ArrayList<>();
    private static List<String> codesFromFiles = new ArrayList<>();
    private static final XmlMapper xmlMapper = new XmlMapper();
    static {
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CodeCheckerService(String eventId) {
        CodeCheckerService.eventId = eventId;
    }

    @Override
    public void run() {
        start();
    }

    private void start(){
        LocalDateTime start = LocalDateTime.now();

        String eventId = CodeCheckerService.eventId;

        List<Path> files = readFiles();

        files.forEach(x -> {
            HierarchyInfoDto info = readFileHierarchy(x);
                log.info(info.getSsccDown().getSsccDown().getSscc());
                saveHierarchyToEventLines(eventId, info);
        });


        boolean isFilesSgtinsValidate = validateFilesSgtinQty();
        boolean isEventValidate = validateLinesInEvent(eventId);

        if (isEventValidate && isFilesSgtinsValidate){
            log.info("Thread {}. Перевожу событие {} в статус Создано", Thread.currentThread().getName(), eventId);
            try {
                UtraceClient.createFromDraftEventById(eventId);
            } catch (IOException e) {
                log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
            }

            log.info("Thread {}. Перевожу событие {} в статус Заполнено", Thread.currentThread().getName(), eventId);
            try {
                UtraceClient.validateEventById(eventId);
            } catch (IOException e) {
                log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
            }

        }

        log.info("Thread {}. Сохранено в событие {} sgtin: {}", Thread.currentThread().getName(), eventId, sgtinCounter);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        log.info("Thread {}. Загрузка строк заняла {} секунд", Thread.currentThread().getName(), duration.getSeconds());
    }

    private static List<Path> readFiles(){
        Path dir = Paths.get(PropertiesConfig.properties.getProperty("code-checker.xml-directory") + CodeCheckerService.eventId);
        List<Path> files = new ArrayList<>();
        try {
            files = Files.walk(dir).collect(Collectors.toList());
            files.remove(dir);
        } catch (IOException e) {
            log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
        }
        return files;
    }

    private static HierarchyInfoDto readFileHierarchy(Path path){
        HierarchyInfoDto hierarchyInfoDto = null;
        try {
            log.info("Thread {}. Читаю файл {}", Thread.currentThread().getName(), path.toString());
            hierarchyInfoDto = xmlMapper.readValue(path.toFile(), Documents.class).getHierarchyInfoDto();
        } catch (NoSuchFileException e) {
            log.error(Thread.currentThread().getName() + " Нет файла : " + e.getLocalizedMessage());
            return null;
        } catch (JsonParseException  e) {
            log.error(Thread.currentThread().getName() + " Ошибка парсинга xml : " + e.getLocalizedMessage());
        } catch (IOException e) {
           log.error(Thread.currentThread().getName() + " Непредвиденная ошибка : " + e.getLocalizedMessage());
        }
        return hierarchyInfoDto;
    }

    private static void saveHierarchyToEventLines (String eventId, HierarchyInfoDto info) {
        SsccInfo rootLine = info.getSsccDown().getSsccDown();
        List<String> filter = new ArrayList<>();
        filter.add("&event.id=" + eventId);
        filter.add("&code=" + rootLine.getSscc());

        PageDtoOfEventLineDto pageDtoOfEventLineDto = null;
        try {
            pageDtoOfEventLineDto = UtraceClient.getPagedEventLinesByFilter(filter);
        } catch (IOException e) {
            log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
        }

        EventLineDto rootSscc = pageDtoOfEventLineDto.getEventLineDtos().get(0);
        log.info("Thread {}. Обрабатываю {} ", Thread.currentThread().getName(), rootSscc.getCode());
        if (rootLine.getChilds().getSsccList() == null){
            saveSgtinsToBoxes(rootLine.getChilds().getSgtinList(), rootSscc, eventId);
        }
        else {
            saveBoxesToPallet(rootLine, rootSscc, eventId);
        }
    }

    private static void saveBoxesToPallet(SsccInfo ssccInfo, EventLineDto sscc, String eventId) {

        ssccInfo.getChilds().getSsccList().forEach(s -> {
                EventLineDto ssccToSave = new EventLineDto();
                ssccToSave.setCode(s.getSscc());
                ssccToSave.setSscc(true);
                ssccToSave.setParentLineDto(sscc);


                log.info("Thread {}. Сохраняю SSCC {} для события {}, число SGTIN {}",
                        Thread.currentThread().getName(), s.getSscc(), eventId, s.getChilds().getSgtinList().size());

                EventLineDto savedSscc = null;
                try {
                    savedSscc = UtraceClient.postLinesForMoveOwnerNotif(ssccToSave, eventId);
                } catch (IOException e) {
                    List<String> ssccFilter = new ArrayList<>();
                    ssccFilter.add("&code=" + ssccToSave.getCode());
                    ssccFilter.add("&event.id=" + eventId);

                    PageDtoOfEventLineDto pageDtoOfEventLineDto = null;
                    try {
                        pageDtoOfEventLineDto = UtraceClient.getPagedEventLinesByFilter(ssccFilter);
                    } catch (IOException ioException) {
                        log.error(Thread.currentThread().getName() + ioException.getLocalizedMessage());
                    }
                    savedSscc = pageDtoOfEventLineDto.getEventLineDtos().get(0);
                }

                log.info("Thread {}. Сохраняю SGTIN в SSCC {} для события {}, число SGTIN {}",
                        Thread.currentThread().getName(), s.getSscc(), eventId, s.getChilds().getSgtinList().size());
                saveSgtinsToBoxes(s.getChilds().getSgtinList(), savedSscc, eventId);
        });
    }

    private static void saveSgtinsToBoxes(List<SgtinInfo> sgtinInfoList, EventLineDto sscc, String eventId){
        List<BatchInfoDto> incomingBatchInfoDtos = sgtinInfoList.stream()
                .map(x -> {
                    BatchInfoDto batchInfoDto = new BatchInfoDto();
                    batchInfoDto.setBatchOrLot(x.getSeriesNumber());
                    batchInfoDto.setDateExp(convertDates(x.getExpirationDate()));
                    batchInfoDto.setDateMdf("2020-11-10");
                    batchInfoDto.setTradeItemBriefDto(getTiForBatchInfo(x.getGtin()));
                    return batchInfoDto;})
                .collect(Collectors.toList());

        incomingBatchInfoDtos.forEach(x -> {
            try {
                findOrCreateBatchInfo(x);
            } catch (IOException e) {
                log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
            }
        });

        List<EventLineDto> eventLineDtos = sgtinInfoList.stream()
                .map(x -> {
                        EventLineDto eventLineDto = new EventLineDto();
                        eventLineDto.setCode(x.getSgtin());
                        eventLineDto.setParentLineDto(sscc);
                        eventLineDto.setSscc(false);

                        BatchInfoDto batchInfoDto = batchBriefDtos.stream()
                                .filter(b -> b.getBatchOrLot().equals(x.getSeriesNumber())
                                        && b.getTradeItemBriefDto().getGtin().equals(x.getGtin()))
                                .findFirst().orElseThrow();
                        eventLineDto.setBatchInfoDto(batchInfoDto);

                        return eventLineDto;
                    })
                .collect(Collectors.toList());

        try {
            UtraceClient.postLinesForMoveOwnerNotif(eventLineDtos, eventId);
            countSgtins(eventLineDtos);
        } catch (IOException e) {
            log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
            log.error("Thread {}. Один или несколько кодов уже сохранены. Вычисляю разницу", Thread.currentThread().getName());

            List<String> sgtinFilter = new ArrayList<>();
            eventLineDtos.forEach(x -> sgtinFilter.add("&code=" + x.getCode()));
            sgtinFilter.add("&event.id=" + eventId);
            sgtinFilter.add("&size=2000");

            try {
                final List<String> savedCodes = UtraceClient.getPagedEventLinesByFilter(sgtinFilter).getEventLineDtos().stream()
                        .map(EventLineDto::getCode)
                        .collect(Collectors.toList());

                List<EventLineDto> notSavedCodes = eventLineDtos.stream()
                        .filter(x -> !savedCodes.contains(x.getCode()))
                        .collect(Collectors.toList());

                if (!notSavedCodes.isEmpty()) {
                    log.error("Thread {}. Сохраняю разницу", Thread.currentThread().getName());
                    UtraceClient.postLinesForMoveOwnerNotif(notSavedCodes, eventId);
                }
                else
                    log.error("Thread {}. Все коды сохранены", Thread.currentThread().getName());

                countSgtins(eventLineDtos);
            } catch (IOException ioException) {
                log.error(Thread.currentThread().getName() + ioException.getLocalizedMessage());
            }
        }
    }

    private static String convertDates(String oldDate){
        Pattern pattern = Pattern.compile("([0-9]{2}).([0-9]{2}).([0-9]{4})");
        Matcher matcher = pattern.matcher(oldDate);
        matcher.matches();

        StringBuilder newDate = new StringBuilder();
        newDate
                .append(matcher.group(3))
                .append("-")
                .append(matcher.group(2))
                .append("-")
                .append(matcher.group(1));

        return newDate.toString();
    }

    private static TradeItemBriefDto getTiForBatchInfo(String gtin){
        TradeItemBriefDto tradeItemBriefDto = null;

        if (!tradeItemBriefDtos.isEmpty()){
            tradeItemBriefDto = tradeItemBriefDtos.stream()
                    .filter(x -> x.getGtin().equals(gtin))
                    .findFirst().orElseThrow();
        }
        if (tradeItemBriefDto == null){
            List<String> tiFilter = new ArrayList<>();
            tiFilter.add("&gtin=" + gtin);
            PageTradeItemBriefDto pageTradeItemBriefDto;
            try {
                pageTradeItemBriefDto = UtraceClient.getPagedTradeItemsByFilter(tiFilter);
                tradeItemBriefDto = pageTradeItemBriefDto.getTradeItemBriefDtos().get(0);
                tradeItemBriefDtos.add(tradeItemBriefDto);
            } catch (IOException e) {
                log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
            }
        }
        return tradeItemBriefDto;
    }

    private static void findOrCreateBatchInfo(BatchInfoDto incomingBatchInfo) throws IOException {
        BatchInfoDto currBatchInfo = null;
        if (!batchBriefDtos.isEmpty()){
            currBatchInfo = batchBriefDtos.stream()
                    .filter(x -> x.getBatchOrLot().equals(incomingBatchInfo.getBatchOrLot())
                            && x.getTradeItemBriefDto().getId().equals(incomingBatchInfo.getTradeItemBriefDto().getId()))
                    .findFirst().orElseThrow();
        }
        if (currBatchInfo == null){
            List<String> biFilter = new ArrayList<>();
            biFilter.add("&batchOrLot=" + incomingBatchInfo.getBatchOrLot());
            biFilter.add("&tradeItem.id=" + incomingBatchInfo.getTradeItemBriefDto().getId());

            PageBatchInfoDto pageBatchInfoDto = UtraceClient.getPagedBatchInfoByFilter(biFilter);

            if (!pageBatchInfoDto.getBatchInfoDtos().isEmpty()){
                currBatchInfo = pageBatchInfoDto.getBatchInfoDtos().get(0);
            }
            else {
                currBatchInfo = UtraceClient.postBatchInfo(incomingBatchInfo);
            }
            batchBriefDtos.add(currBatchInfo);
        }
    }

    private static void countSgtins(List<EventLineDto> eventLineDtos){
        sgtinCounter += eventLineDtos.size();
    }

    private static boolean validateFilesSgtinQty(){
        List<Path> paths = readFiles();
        log.info("Thread {}. Запускаю валидацию - сверка считанных SGTIN и SGTIN из сообщений", Thread.currentThread().getName());

        int qtySgtins = paths.stream()
                .map(CodeCheckerService::readFileHierarchy)
                .filter(Objects::nonNull)
                .mapToInt(CodeCheckerService::validatorSgtinsInSscc)
                .sum();
        log.info("Thread {}. Всего в файлах находится {} SGTIN", Thread.currentThread().getName(),  qtySgtins);

        boolean isValidate = false;
        if (sgtinCounter != 0){
            if (!(sgtinCounter == qtySgtins)) {
                log.info("Thread {}. Считанное при загрузке количество SGTIN и при валидации не сходится! " +
                        "SGTIN при валидации {}, считано при загрузке {}", Thread.currentThread().getName(), qtySgtins, sgtinCounter);
            }
            else {
                log.info("Thread {}. Валидация успешна! Количество SGTIN и считанных через кодЧекер сходится!", Thread.currentThread().getName());
                isValidate = true;
            }
        }

        return isValidate;
    }

    private static int validatorSgtinsInBox(SsccInfo ssccInfo){
        log.info("Thread {}. Валидирую количество товара в коробе {}", Thread.currentThread().getName(),  ssccInfo.getSscc());
        return ssccInfo.getChilds().getSgtinList().size();
    }

    private static int validatorSgtinsInSscc(HierarchyInfoDto hierarchyInfoDto){
        log.info("Thread {}. Валидирую количество товаров в SSCC {}", Thread.currentThread().getName(),  hierarchyInfoDto.getSsccDown().getSsccDown().getSscc());
        int count;

        if (hierarchyInfoDto.getSsccDown().getSsccDown().getChilds().getSgtinList() == null){
            count = hierarchyInfoDto.getSsccDown().getSsccDown().getChilds().getSsccList().stream()
                    .mapToInt(CodeCheckerService::validatorSgtinsInBox)
                    .sum();
        }
        else {
            count = validatorSgtinsInBox(hierarchyInfoDto.getSsccDown().getSsccDown());
        }

        return count;
    }

    private static boolean validateLinesInEvent(String eventId){
        List<String> filter = new ArrayList<>();
        filter.add("&event.id=" + eventId);
        filter.add("&isSscc=false");
        filter.add("&size=1");

        boolean isValidate = false;
        try {
            PageDtoOfEventLineDto pageDtoOfEventLineDto = UtraceClient.getPagedEventLinesByFilter(filter);
            log.info("Thread {}. Валидация сохраненного в события количества SGTIN. Событие {}, сохранено {}",
                    Thread.currentThread().getName(),
                    eventId,
                    pageDtoOfEventLineDto.getPage().getTotalElements());

            if (sgtinCounter != 0){
                if (!(sgtinCounter == pageDtoOfEventLineDto.getPage().getTotalElements())) {
                    log.info("Thread {}. Событие {}. Сохраненное количество SGTIN и считанное из файлов не совпадает! " +
                                    "SGTIN сохранено {}, создано строк {}",
                            Thread.currentThread().getName(),
                            eventId,
                            pageDtoOfEventLineDto.getPage().getTotalElements(),
                            sgtinCounter);
                }
                else {
                    log.info("Thread {}. Валидация успешна! Количество SGTIN и созданных строк в событии {} сходится!",
                            Thread.currentThread().getName(),
                            eventId);
                    isValidate = true;
                }
            }

        } catch (IOException e) {
            log.error(Thread.currentThread().getName() + e.getLocalizedMessage());
        }

        return isValidate;
    }

}
