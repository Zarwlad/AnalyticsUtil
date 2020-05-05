package ru.zarwlad.mdlp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.zarwlad.mdlp.dto.messages.Documents;
import ru.zarwlad.mdlp.dto.messages.eventLinePostDto.EventLinePostDto;
import ru.zarwlad.mdlp.dto.messages.multiPackDto.DetailDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShireCaseService {
    public static void main(String[] args) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        System.out.println("Введи путь к файлу, multiPack из Unitrace");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        String mdlpRequest = consoleReader.readLine();
        Path mdlpReqPath = Paths.get(mdlpRequest);
        Documents mdlpRequestDoc = xmlMapper.readValue(Files.readAllBytes(mdlpReqPath), Documents.class);

        List<EventLinePostDto> eventLinePostDtos = new ArrayList<>();

        for (DetailDTO d : mdlpRequestDoc.getMultiPackDTO().getDetail()) {
            System.out.println("\n\n Записываю содержимое кода " + d.getSscc());
            for (String s : d.getSgtin()) {
                EventLinePostDto eventLinePostDto = new EventLinePostDto();
                eventLinePostDto.setCode(s);
                eventLinePostDto.setSscc(false);
                eventLinePostDto.setParent(null);
                eventLinePostDtos.add(eventLinePostDto);
            }
        }

        List<EventLinePostDto> ssccs = new ArrayList<>();

        for (DetailDTO d : mdlpRequestDoc.getMultiPackDTO().getDetail()) {
            System.out.println("\n Записываю сам код " + d.getSscc());
            EventLinePostDto eventLinePostDto = new EventLinePostDto();
            eventLinePostDto.setCode(d.getSscc());
            eventLinePostDto.setParent(null);
            eventLinePostDto.setSscc(true);
            ssccs.add(eventLinePostDto);
        }


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

        Path path = Files.createFile(Paths.get("C:\\Users\\Vladimir\\Desktop\\shire200319\\"
                + mdlpReqPath.getFileName() + "____"
                + LocalDateTime.now().format(dateTimeFormatter) + ".json"));

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new FileWriter(path.toFile()), eventLinePostDtos);


        Path ssccPath = Files.createFile(Paths.get("C:\\Users\\Vladimir\\Desktop\\shire200319\\"
                + mdlpReqPath.getFileName() + "__sscc__"
                + LocalDateTime.now().format(dateTimeFormatter) + ".json"));
        objectMapper.writeValue(new FileWriter(ssccPath.toFile()), ssccs);
    }
}
