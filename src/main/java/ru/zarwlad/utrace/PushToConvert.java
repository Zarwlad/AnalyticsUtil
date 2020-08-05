package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.MessageDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.service.MultiThreadEventDownloader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PushToConvert {
    private static Logger log = LoggerFactory.getLogger(PushToConvert.class);

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }
        PageMessageDto messageDto = UtraceClient.getPagedMessagesList(true, null);

        for (MessageDto id : messageDto.getMessageDtos()) {
            //Thread.sleep(500);

            List<String> events = UtraceClient.postProcessDefaultConvMsg(id.getId());

            log.info("messageId: {}, создано событий: {}", id, events.size());
        }
    }

    public static class D implements Runnable{
        List<String> ids;

        @Override
        public void run() {
            for (String id : ids) {
                //Thread.sleep(500);

                List<String> events = UtraceClient.postProcessDefaultConvMsg(id);

                log.info("{}, messageId: {}, создано событий: {}", Thread.currentThread(), id, events.size());
            }
        }
    }
}
