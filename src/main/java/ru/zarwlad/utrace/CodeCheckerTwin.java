package ru.zarwlad.utrace;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.service.AuthService;
import ru.zarwlad.utrace.service.CodeCheckerService;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.Documents;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.ChildCode;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.HierarchyInfoDto;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.SgtinInfo;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.SsccInfo;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.BatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.BatchBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.TradeItemBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageBatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageTradeItemBriefDto;
import ru.zarwlad.utrace.util.client.PropertiesConfig;
import ru.zarwlad.utrace.util.client.UtraceClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.zarwlad.utrace.service.CodeCheckerService.*;

public class CodeCheckerTwin {
    private static final Logger log = LoggerFactory.getLogger(CodeCheckerTwin.class);

    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<String> eventIds = Arrays.asList(PropertiesConfig.properties.getProperty("code-checker.event-ids").split(";"));

        List<Thread> threads = new ArrayList<>();

        eventIds.forEach(x -> {
            CodeCheckerService codeCheckerService = new CodeCheckerService(x);
            Thread thread = new Thread(codeCheckerService, "t-" + x);
            threads.add(thread);
            threads.forEach(t -> {
                t.start();
            });
        });
    }
}
