package ru.zarwlad.sgtinsFromMdlpAnalitics;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.HierarchySsccMdlpDto;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.utrace.utraceDto.pagedDtos.PageDtoOfCodeTreeRootDto;
import ru.zarwlad.utrace.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.utrace.utraceDto.xmlDto.OrderDetailsDto;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static ru.zarwlad.utrace.service.DownloadEventsService.getResponseBody;

public class ChiesiCodesExporting {
    private static Logger log = LoggerFactory.getLogger(ChiesiCodesExporting.class);
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

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

    static String eventId = "4f6930da-d411-4df5-b910-8c7b25e38451";

    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<EventLineDto> eventCodesUt = new ArrayList<>();

        int i = 0;

        PageDtoOfEventLineDto pageDtoOfEventLineDto = getPageEventLinesFromEvent(eventId, i);
        while (i >= 0){
            eventCodesUt.addAll(pageDtoOfEventLineDto.getEventLineDtos());

            if (pageDtoOfEventLineDto.getPage().getTotalPages() == 1 ||
            i == pageDtoOfEventLineDto.getPage().getTotalPages())
                i = -1;
            else {
                i++;
                pageDtoOfEventLineDto = getPageEventLinesFromEvent(eventId, i);
            }
        }

        OrderDetailsDto codesInUtraceAndMdlp = new OrderDetailsDto(new ArrayList<>(), new ArrayList<>());
        OrderDetailsDto codesInUtraceOnly = new OrderDetailsDto(new ArrayList<>(), new ArrayList<>());
        OrderDetailsDto codesInMdlpOnly = new OrderDetailsDto(new ArrayList<>(), new ArrayList<>());

        for (EventLineDto eventLineDto : eventCodesUt) {
            log.info(eventLineDto.toString());
            HierarchySsccMdlpDto hierarchySsccMdlpDto = getMdlpHierarchy(eventLineDto.getCode());

            PageDtoOfCodeTreeRootDto pageDtoOfCodeTreeRootDto = getPagedCodeTreeRootsByCode(eventLineDto.getCode());

            if (hierarchySsccMdlpDto != null && pageDtoOfCodeTreeRootDto.getCodeTreeRootDtos() != null){
                if (eventLineDto.isSscc())
                    codesInUtraceAndMdlp.getSscc().add(eventLineDto.getCode());
                else
                    codesInUtraceAndMdlp.getSgtin().add(eventLineDto.getCode());
            }
            else if (hierarchySsccMdlpDto != null){
                if (eventLineDto.isSscc())
                    codesInMdlpOnly.getSscc().add(eventLineDto.getCode());
                else
                    codesInMdlpOnly.getSgtin().add(eventLineDto.getCode());
            }
            else if (pageDtoOfCodeTreeRootDto.getCodeTreeRootDtos() != null){
                if (eventLineDto.isSscc())
                    codesInUtraceOnly.getSscc().add(eventLineDto.getCode());
                else
                    codesInUtraceOnly.getSgtin().add(eventLineDto.getCode());
            }
        }

        Path fileCodesInUtraceAndMdlp = Files.createFile(
                Paths.get("src\\reports\\"
                        + "Event_"
                        + eventId
                        + "_"
                        + "CodesInUtraceAndMdlp"
                        + LocalDateTime.now().format(dateTimeFormatter) + ".xml"));

        Path fileCodesInUtraceOnly = Files.createFile(
                Paths.get("src\\reports\\"
                        + "Event_"
                        + eventId
                        + "_"
                        + "CodesInUtraceOnly"
                        + LocalDateTime.now().format(dateTimeFormatter) + ".xml"));

        Path fileCodesInMdlpOnly = Files.createFile(
                Paths.get("src\\reports\\"
                        + "Event_"
                        + eventId
                        + "_"
                        + "CodesInMdlpOnly"
                        + LocalDateTime.now().format(dateTimeFormatter) + ".xml"));

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        xmlMapper.writeValue(fileCodesInUtraceAndMdlp.toFile(), codesInUtraceAndMdlp);
        xmlMapper.writeValue(fileCodesInUtraceOnly.toFile(), codesInUtraceOnly);
        xmlMapper.writeValue(fileCodesInMdlpOnly.toFile(), codesInMdlpOnly);
    }

    static PageDtoOfEventLineDto getPageEventLinesFromEvent (String eventId, int page) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/event-lines"
                + "?event.id=" + eventId
                + "&page=" + page;
        String str = getResponseBody(urlPath);

        return objectMapper
                .readValue(str, PageDtoOfEventLineDto.class);
    }

    static HierarchySsccMdlpDto getMdlpHierarchy (String sscc) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String urlPath = properties.getProperty("host")
                + "mdlp/api/sscc/"
                + sscc
                + "/hierarchy";
        String str = getResponseBody(urlPath);

        HierarchySsccMdlpDto hierarchySsccMdlpDto = null;
        try {
            hierarchySsccMdlpDto =
                    objectMapper.readValue(str, HierarchySsccMdlpDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hierarchySsccMdlpDto.getUp() != null){
            log.info(hierarchySsccMdlpDto.toString());
            return  hierarchySsccMdlpDto;
        }
        else {
            log.info("Пустая иерархия на запросе sscc " + sscc);
            return null;
        }
    }

    static PageDtoOfCodeTreeRootDto getPagedCodeTreeRootsByCode (String code) throws IOException {
        String urlPath = properties.getProperty("host")
                + "api/2.0/codetreeroots"
                + "?code.sgtinOrSscc=" + code;
        String str = getResponseBody(urlPath);

        log.info(str);

        return objectMapper
                .readValue(str, PageDtoOfCodeTreeRootDto.class);
    }

}
