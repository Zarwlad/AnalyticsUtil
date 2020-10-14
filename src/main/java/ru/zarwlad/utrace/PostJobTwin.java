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
import java.util.Collections;
import java.util.List;

public class PostJobTwin implements Runnable{
    static Logger log = LoggerFactory.getLogger(PostJobTwin.class);

    @Override
    public void run() {
            PostJobTwin.main(null);
    }

    public static void main(String[] args) {

        DateTimeUtil.currentDateTime = LocalDateTime.now();
        log.info("Аутенфикация, время {}", DateTimeUtil.currentDateTime);
        try {
            AuthService.Auth();
        } catch (
                IOException e) {
            log.error(e.toString());
        }

        while (true){
            try {
                Duration duration = Duration.between(DateTimeUtil.currentDateTime, LocalDateTime.now());
                if (duration.getSeconds() > 12600L) {
                    DateTimeUtil.currentDateTime = LocalDateTime.now();
                    log.info("Аутенфикация, время {}", DateTimeUtil.currentDateTime);
                    try {
                        AuthService.Auth();
                    } catch (
                            IOException e) {
                        log.error(e.toString());
                    }
                }
                List<String> locations = new ArrayList<>();
                Collections.addAll(locations,
                        "0c49828b-296e-454e-94a3-86eb3112cea8",
                        "3b2aeff3-8ebf-41db-a297-f9ce126f2806",
                        "708ae89b-dec8-412d-bc3b-4065dff71c61",
                        "7eaf8486-9b8e-4829-99fe-ed654c59593a");

                List<Thread> threads = new ArrayList<>();

                locations.forEach(x -> threads.add(new Thread(new PostJobTwinService(x))));

                threads.forEach(Thread::start);

                threads.forEach(x -> {
                    try {
                        x.join();
                    } catch (InterruptedException ignored) {
                    }
                });

                Thread.sleep(300000);
            }
            catch (Exception e){
                continue;
            }
        }

    }
}
