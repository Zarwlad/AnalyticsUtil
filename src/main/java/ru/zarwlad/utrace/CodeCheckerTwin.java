package ru.zarwlad.utrace;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.Documents;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.HierarchyInfoDto;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.SgtinInfo;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.SsccInfo;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.BatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.BatchBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.TradeItemBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageBatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageTradeItemBriefDto;
import ru.zarwlad.utrace.util.client.PropertiesConfig;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CodeCheckerTwin {
    private static Logger log = LoggerFactory.getLogger(CodeCheckerTwin.class);
    private static XmlMapper xmlMapper = new XmlMapper();
    static {
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static List<BatchInfoDto> batchBriefDtos = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        Path dir = Paths.get(PropertiesConfig.properties.getProperty("code-checker.xml-directory"));
        String eventId = PropertiesConfig.properties.getProperty("code-checker.event-id");

        List<Path> files = Files.walk(dir).collect(Collectors.toList());

        files.stream()
                .filter(x -> !x.equals(dir))
                .forEach(x -> {
            HierarchyInfoDto info = readFileHierarchy(x);
            try {
                saveHierarchyToEventLines(eventId, info);
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            }
        });

    }

    private static HierarchyInfoDto readFileHierarchy(Path path){
        try {
            log.info("Читаю файл {}", path.toString());
            return xmlMapper.readValue(path.toFile(), Documents.class).getHierarchyInfoDto();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    private static void saveHierarchyToEventLines (String eventId, HierarchyInfoDto info) throws IOException {
        SsccInfo rootLine = info.getSsccDown().getSsccDown();
        List<String> filter = new ArrayList<>();
        filter.add("&event.id=" + eventId);
        filter.add("&code=" + rootLine.getSscc());

        PageDtoOfEventLineDto pageDtoOfEventLineDto = UtraceClient.getPagedEventLinesByFilter(filter);

        EventLineDto rootSsscc = pageDtoOfEventLineDto.getEventLineDtos().get(0);
        log.info("Обрабатываю {} ", rootSsscc.getCode());
        if (rootLine.getChilds().getSsccList().isEmpty()){
            saveSgtinsToBoxes(rootLine.getChilds().getSgtinList(), rootSsscc, eventId);
        }
        else {
            saveBoxesToPallet(rootLine, rootSsscc, eventId);
        }
    }

    private static void saveBoxesToPallet(SsccInfo ssccInfo, EventLineDto sscc, String eventId) throws IOException {

        for (SsccInfo s : ssccInfo.getChilds().getSsccList()) {
            EventLineDto ssccToSave = new EventLineDto();
            ssccToSave.setCode(s.getSscc());
            ssccToSave.setSscc(true);
            ssccToSave.setParentLineDto(sscc);
            log.info("Сохраняю SSCC {} для события {}, число SGTIN {}", s.getSscc(), eventId, s.getChilds().getSgtinList().size());
            EventLineDto savedSscc = UtraceClient.postLinesForMoveOwnerNotif(ssccToSave, eventId);

            log.info("Сохраняю SGTIN в SSCC {} для события {}, число SGTIN {}", s.getSscc(), eventId, s.getChilds().getSgtinList().size());
            saveSgtinsToBoxes(s.getChilds().getSgtinList(), savedSscc, eventId);
        }
    }

    private static void saveSgtinsToBoxes(List<SgtinInfo> sgtinInfoList, EventLineDto sscc, String eventId){
        List<BatchInfoDto> incomingBatchInfoDtos = sgtinInfoList.stream()
                .map(x -> {
            BatchInfoDto batchInfoDto = new BatchInfoDto();
            batchInfoDto.setBatchOrLot(x.getSeriesNumber());
            batchInfoDto.setDateExp(convertDates(x.getExpirationDate()));
            batchInfoDto.setTradeItemBriefDto(getTiForBatchInfo(x.getGtin()));
            return batchInfoDto;})
                .collect(Collectors.toList());

        incomingBatchInfoDtos.forEach(x -> {
            try {
                findOrCreateBatchInfo(x);
            } catch (IOException e) {
               log.error(e.getLocalizedMessage());
            }
        });

        List<EventLineDto> eventLineDtos = sgtinInfoList.stream().map(x -> {
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
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
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
        List<String> tiFilter = new ArrayList<>();
        tiFilter.add("&gtin=" + gtin);
        PageTradeItemBriefDto pageTradeItemBriefDto = null;
        try {
            pageTradeItemBriefDto = UtraceClient.getPagedTradeItemsByFilter(tiFilter);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        return pageTradeItemBriefDto.getTradeItemBriefDtos().get(0);
    }

    private static BatchInfoDto findOrCreateBatchInfo(BatchInfoDto incomingBatchInfo) throws IOException {
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
        return currBatchInfo;
    }
}
