package ru.zarwlad.util.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

class PropertiesConfig {
    private static Logger log = LoggerFactory.getLogger(PropertiesConfig.class);

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(
                    new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
