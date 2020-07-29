package ru.zarwlad.mdlp.downloadMdlpPartners.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.rznDto.LicencesListDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class RznParserService {
    private static XmlMapper xmlMapper = new XmlMapper();
    static {
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    }
    private static Logger log = LoggerFactory.getLogger(RznParserService.class);
    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LicencesListDto startParsing(){
        Path xmlFile = Paths.get(properties.getProperty("rznFile"));

        LicencesListDto licencesListDto = null;
        try {
            licencesListDto = xmlMapper.readValue(xmlFile.toFile(), LicencesListDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return licencesListDto;
    }
}
