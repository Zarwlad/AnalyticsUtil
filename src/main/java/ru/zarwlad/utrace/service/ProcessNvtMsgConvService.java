package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessNvtMsgConvService {
    static Logger log = LoggerFactory.getLogger(ProcessNvtMsgConvService.class);

    public static void processNvtMessages() throws IOException {
        List<String> filter = new ArrayList<>();
        filter.add("&size=200");
        filter.add("&sort=created,asc");
        filter.add("&integrationDirection.id=");
        PageMessageDto messageDtos = UtraceClient.getPagedMessagesByFilter(filter);
    }
}
