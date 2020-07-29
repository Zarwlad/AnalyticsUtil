package ru.zarwlad.sgtinsFromMdlpAnalitics;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.mdlpDto.HierarchySsccMdlpDto;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfCodeTreeRootDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.unitedDtos.mdlpDto.documentDto.OrderDetailsDto;

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

import static ru.zarwlad.util.client.MdlpClient.getMdlpHierarchy;
import static ru.zarwlad.util.client.UtraceClient.*;

public class ChiesiCodesExporting {
    private static Logger log = LoggerFactory.getLogger(ChiesiCodesExporting.class);
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
}
