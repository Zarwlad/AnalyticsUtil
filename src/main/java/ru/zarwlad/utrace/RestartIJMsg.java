package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestartIJMsg {
    private static Logger log = LoggerFactory.getLogger(RestartIJMsg.class);

    public static void main(String[] args) throws IOException {

        DateTimeUtil.currentDateTime = LocalDateTime.now();
        log.info("Аутенфикация, время {}", DateTimeUtil.currentDateTime);
        try {
            AuthService.Auth();
        } catch (
                IOException e) {
            log.error(e.toString());
        }

        List<String> filter = new ArrayList<>();
        filter.add("&size=2000");
        filter.add("&sort=operationDate,asc");
        filter.add("&integrationDirection.id=9ca1e332-fe69-4d2a-af69-1daebf62d067");
        filter.add("&status=TECH_ERROR");
        PageMessageDto messageDto = UtraceClient.getPagedMessagesByFilter(filter);

        messageDto.getMessageDtos().forEach(x -> {
            try {
                UtraceClient.getMsgResetErrors(x.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
