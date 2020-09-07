package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.PostJobTwinService;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostJobTwin implements Runnable{
    static Logger log = LoggerFactory.getLogger(PostJobTwin.class);

    @Override
    public void run() {
        try {
            PostJobTwin.main(null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        DateTimeUtil.currentDateTime = LocalDateTime.now();
        log.info("Аутенфикация, время {}", DateTimeUtil.currentDateTime);
        try {
            AuthService.Auth();
        } catch (
                IOException e) {
            log.error(e.toString());
        }

        while (true){
            Duration duration = Duration.between(DateTimeUtil.currentDateTime, LocalDateTime.now());
            if (duration.getSeconds() > 12600L){
                DateTimeUtil.currentDateTime = LocalDateTime.now();
                log.info("Аутенфикация, время {}", DateTimeUtil.currentDateTime);
                try {
                    AuthService.Auth();
                } catch (
                        IOException e) {
                    log.error(e.toString());
                }
            }
            Thread thread = new Thread(new PostJobTwinService());
            thread.start();
            thread.join();
            Thread.sleep(300000);
        }

    }
}
