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
        try {
            PostJobTwin.main(null);
        } catch (IOException | InterruptedException e) {
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
            List<String> locations = new ArrayList<>();
            Collections.addAll(locations,
                    "b401db51-c7fd-42ac-86c0-2703684b3cf6",
                    "65bbb0eb-e4df-4f0d-8595-df636c1a16bd",
                    "2ba33818-7604-4a9c-afc6-777de504c7c1",
                    "b5ee792f-b87e-4b8a-91c2-17cbb486c10f",
                    "c36646c3-dc84-4bca-9f44-75b2e83fce87",
                    "87d5d103-953a-4ba5-aa99-94881257b6d6",
                    "58ec89d5-8e9b-4e86-8f99-22f7b170f102",
                    "de384cd9-b18b-4567-8ee5-0e14d7da669b",
                    "c514bfab-0fa5-4d9a-9796-c58f691ccbbd",
                    "6367ff73-d77f-4fa4-9646-a7f5dd811234",
                    "2cf508c6-e6b8-4a00-be15-f7e4e590342e",
                    "c6697a09-8b30-46ce-a14b-01441b2bf444",
                    "455b9597-4198-4e90-a314-ee0ef3492e0e",
                    "8cac6dc8-bbe9-4557-858a-4b537e1b6c96");

            List<Thread> threads = new ArrayList<>();

            locations.forEach(x -> threads.add(new Thread(new PostJobTwinService(x))));

            threads.forEach(Thread::start);

            threads.forEach(x -> {try {x.join();} catch (InterruptedException ignored){}});

            Thread.sleep(300000);
        }

    }
}
