package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.service.FileReaderService;
import ru.zarwlad.utrace.service.MultiThreadRestart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestartMsgIJ {
    private static Logger log = LoggerFactory.getLogger(RestartMsgIJ.class);


    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<String> filter = new ArrayList<>();
        filter.add("status=ERROR");
        filter.add("sort=created,desc");
        filter.add("filename=321");
        filter.add("size=2000");
        filter.add("integrationDirection.id=5c727794-4b30-482d-8172-d253b0a346b5");

        PageMessageDto messageDtos = UtraceClient.getPagedMessagesList(true, filter);
        List<String> strings = new ArrayList<>();

        for (MessageDto messageDto : messageDtos.getMessageDtos()) {
            strings.add(messageDto.getId());
        }

        int limit = 200;

        List<String> batchId = new ArrayList<>();

        List<List<String>> bathes = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            batchId.add(strings.get(i));
            if (batchId.size() == limit){
                bathes.add(batchId);
                batchId = new ArrayList<>();
            }
            if (i == strings.size()-1){
                bathes.add(batchId);
            }
        }

        for (List<String> batch : bathes) {
            MultiThreadRestart mtr = new MultiThreadRestart(batch);
            Thread thread = new Thread(mtr);

            log.info(thread.getName());

            thread.start();
        }

//        MultiThreadRestart mt = new MultiThreadRestart(strings);
//        Thread thread = new Thread(mt);
//        thread.start();

    }
}
