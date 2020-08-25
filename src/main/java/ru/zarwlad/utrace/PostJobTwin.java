package ru.zarwlad.utrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos.PageMessageDto;
import ru.zarwlad.utrace.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostJobTwin {
    static Logger log = LoggerFactory.getLogger(PostJobTwin.class);

    public static void main(String[] args) throws IOException {
        //PostJobTwinService.PostJobProcess();

        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (
                IOException e) {
            log.error(e.toString());
        }

        List<String> filterForStFormat = new ArrayList<>();
        filterForStFormat.add("&integrationDirection.id=abe989cb-d991-4440-8c70-1c0527335611");
        filterForStFormat.add("&page=0");
        filterForStFormat.add("&size=2000");
        filterForStFormat.add("&status=CREATED");
        filterForStFormat.add("&sort=created,asc");

        PageMessageDto messageDto = UtraceClient.getPagedMessagesByFilter(filterForStFormat);

        List<String> errorArgs = new ArrayList<>();
        errorArgs.add("Artificial error");
        messageDto.getMessageDtos().forEach(x -> {
            System.out.println(x.getDocumentType() + " " + x.getFilename());

            UtraceClient.postErrorForMessage(
                    x.getId(),
                    "http://integration-journal/journal/api/1.0/integration-error-message/get-message-text-from-bundle",
                    "ERROR",
                    "J000007",
                    errorArgs);
        });
    }
}
