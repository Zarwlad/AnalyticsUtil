package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.service.FilterService;
import ru.zarwlad.utrace.service.MultiThreadRestart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestartMsgIJ {
    private static Logger log = LoggerFactory.getLogger(RestartMsgIJ.class);


    public static void main(String[] args) {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<String> strings = new ArrayList<>();

        try {
            strings = FilterService.readIdsFromFile();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        int limit = 2000;

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
