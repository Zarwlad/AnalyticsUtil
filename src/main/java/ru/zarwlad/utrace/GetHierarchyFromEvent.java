package ru.zarwlad.utrace;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvRecurse;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.BatchSgtinQuantityDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.utrace.util.client.PropertiesConfig;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetHierarchyFromEvent {
    private static Logger log = LoggerFactory.getLogger(GetHierarchyFromEvent.class);

    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<String> eventIds = new ArrayList<>(Arrays.asList(PropertiesConfig.properties.getProperty("sup.hierarchy.eventId").split(";")));

        for (String eventId : eventIds) {
            processReports(eventId);
        }
    }

    private static void processReports(String eventId) throws IOException {
        log.info("EventId = {}", eventId);
        PageDtoOfEventLineDto eventLineDto = UtraceClient.getPageEventLinesFromEvent(eventId, 0);

        log.info("Извлечены строки по событию, всего строк {}", eventLineDto.getPage().getTotalElements());
        List<EventLineWithBatch> eventLineWithBatches = eventLineDto.getEventLineDtos().stream()
                .map(x -> {
                    log.info("Извлекаю партию по sscc {}", x.getCode());
                    return new EventLineWithBatch(
                            x, UtraceClient
                            .getPagedBatchesInSscc(x.getCode(), 0, 2000)
                            .getBatchSgtinQuantityDtos());
                })
                .collect(Collectors.toList());

        List<BatchQtyLine> batchQtyLines = createBatchQtyLines(eventLineWithBatches);

        log.info("Печатаю данные в csv");
        printCsvBatchQtyLines(batchQtyLines);
        printCsvBatchQtySums(batchQtyLines);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventLineWithBatch{
        EventLineDto eventLineDto;
        List<BatchSgtinQuantityDto> batchSgtinQuantityDtos;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BatchQtyLine{
        @CsvRecurse
        BatchSgtinQuantityDto batchSgtinQuantityDto;
        @CsvRecurse
        EventLineDto eventLineDto;
    }
    
    private static List<BatchQtyLine>  createBatchQtyLines(List<EventLineWithBatch> lineBatches){
        List<BatchQtyLine> batchQtyLines = new ArrayList<>();
        for (EventLineWithBatch eventLineWithBatch : lineBatches) {
            for (BatchSgtinQuantityDto batchSgtinQuantityDto : eventLineWithBatch.batchSgtinQuantityDtos) {
                BatchQtyLine batchQtyLine = new BatchQtyLine();
                batchQtyLine.setEventLineDto(eventLineWithBatch.eventLineDto);
                batchQtyLine.setBatchSgtinQuantityDto(batchSgtinQuantityDto);
                batchQtyLines.add(batchQtyLine);
            }
        }
        return batchQtyLines;
    }

    private static void printCsvBatchQtyLines(List<BatchQtyLine> batchQtyLines){
        String p = String.format(PropertiesConfig.properties.getProperty("sup.hierarchy.file-path.out"), batchQtyLines.get(0).eventLineDto.getEventDto().getId());
        Path path = Paths.get(p);
        if (Files.notExists(path)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Writer writer = new FileWriter(path.toFile());
            StatefulBeanToCsvBuilder sbtb = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator((';'));
            StatefulBeanToCsv sbt = sbtb
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
                    .build();

            sbt.write(batchQtyLines);
            writer.close();
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private static void printCsvBatchQtySums(List<BatchQtyLine> batchQtyLines){
        String p = String.format(PropertiesConfig.properties.getProperty("sup.batch-hierarchy.file-path.out"), batchQtyLines.get(0).eventLineDto.getEventDto().getId());
        Path path = Paths.get(p);

        List<BatchSgtinQuantityDto> batchSgtinQuantityDtos = new ArrayList<>();
        for (BatchQtyLine batchQtyLine : batchQtyLines) {
            BatchSgtinQuantityDto newBatch = new BatchSgtinQuantityDto();
            newBatch.setBatchBriefDto(batchQtyLine.getBatchSgtinQuantityDto().getBatchBriefDto());
            newBatch.setQuantity(batchQtyLine.batchSgtinQuantityDto.getQuantity());

            boolean exist = false;
            for (BatchSgtinQuantityDto batchInList : batchSgtinQuantityDtos) {
                if (batchInList.getBatchBriefDto().equals(newBatch.getBatchBriefDto())){
                    batchInList.setQuantity(batchInList.getQuantity() + newBatch.getQuantity());
                    exist = true;
                }
            }
            if(!exist){
                batchSgtinQuantityDtos.add(newBatch);
            }
        }

        if (Files.notExists(path)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Writer writer = new FileWriter(path.toFile());
            StatefulBeanToCsvBuilder sbtb = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator((';'));
            StatefulBeanToCsv sbt = sbtb
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.NO_ESCAPE_CHARACTER)
                    .build();

            sbt.write(batchSgtinQuantityDtos);
            writer.close();
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            log.error(e.getLocalizedMessage());
        }
    }

}
