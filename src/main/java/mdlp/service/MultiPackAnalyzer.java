package mdlp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import mdlp.dto.messages.Documents;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MultiPackAnalyzer {
    public static void main(String[] args) throws JAXBException, IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        System.out.println("Введи путь к файлу, отправленному в МДЛП");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        String mdlpRequest = consoleReader.readLine();
        Path mdlpReqPath = Paths.get(mdlpRequest);
        Documents mdlpRequestDoc = xmlMapper.readValue(Files.readAllBytes(mdlpReqPath), Documents.class);

        //String mdlpReq = new String(Files.readAllBytes(mdlpReqPath));


        System.out.println("Введи путь к ответу, полученному из МДЛП");
        String mdlpResponse = consoleReader.readLine();
        Path mdlpRespPath = Paths.get(mdlpResponse);
        Documents mdlpRespTicket = xmlMapper.readValue(Files.readAllBytes(mdlpRespPath), Documents.class);

        int i = 0;

    }
}
