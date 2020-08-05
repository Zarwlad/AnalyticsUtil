package ru.zarwlad.utrace;

import lombok.*;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.dao.EventDao;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.service.DbManager;
import ru.zarwlad.utrace.util.DateTimeUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

public class M {
    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Path fileMsgs = Paths.get("C:\\Users\\vzaremba\\Downloads\\ij_novartis_prod.txt");

        List<String> lines = Files.readAllLines(fileMsgs);
        lines.remove(1);
        lines.remove(0);
        List<Msg> msgs = new ArrayList<>();

        for (String line : lines) {
            String[] lineSplit = line.split("\\|");
            Msg msg = new Msg();
            msg.setId(UUID.fromString(lineSplit[0].trim()));
            msg.setCreated(DateTimeUtil.toZonedDateTime(lineSplit[1].trim()));
            msg.setFilename(lineSplit[2].trim());
            msg.setIntegrationDirectionId(UUID.fromString(lineSplit[6].trim()));
            msg.setDocumentTypeId(lineSplit[7].trim());
            msg.setOperationDate(DateTimeUtil.toZonedDateTime(lineSplit[8].trim()));
            msg.setStatus(lineSplit[9].trim());

            msgs.add(msg);
        }

        Comparator<Msg> msgComparator = Comparator.comparing(Msg::getFilename);

        Collections.sort(msgs, msgComparator);

        Path fileWithDubles = Paths.get("dubles.csv");
        Files.createFile(fileWithDubles);

        Set<Msg> doublesMsg = new HashSet<>();

        for (int i = 0; i < msgs.size(); i++) {
            Msg curr = msgs.get(i);

            if (i != msgs.size() - 1){
                if (!curr.getIntegrationDirectionId().equals(UUID.fromString("9ca1e332-fe69-4d2a-af69-1daebf62d067"))
                        && !curr.getFilename().isEmpty()
                        && !curr.getFilename().equals("income_document.xml")
                        && curr.getFilename().equals(msgs.get(i + 1).getFilename())) {
                    doublesMsg.add(curr);
                    doublesMsg.add(msgs.get(i + 1));
                }
            }
        }
        for (Msg msg : doublesMsg) {
            StringBuilder builder = new StringBuilder();
            builder.append(msg.getId()).append(";");
            builder.append(msg.getCreated()).append(";");
            builder.append(msg.getStatus()).append(";");
            builder.append(msg.getIntegrationDirectionId()).append(";");
            builder.append(msg.getFilename()).append(";");
            builder.append("\n");

            Files.writeString(fileWithDubles, builder, APPEND);
        }
        System.out.println(doublesMsg.size());


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = "id")
    static class Msg {
        UUID id;
        String status;
        ZonedDateTime created;
        ZonedDateTime operationDate;
        String filename;
        UUID integrationDirectionId;
        String documentTypeId;
    }
}
