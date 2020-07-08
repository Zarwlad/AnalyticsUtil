package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.AuthService;

import java.io.IOException;

public class RestartMsgIJ {
    private static Logger log = LoggerFactory.getLogger(RestartMsgIJ.class);


    public static void main(String[] args) {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }



    }
}
