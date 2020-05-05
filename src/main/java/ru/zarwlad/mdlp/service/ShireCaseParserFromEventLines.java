package ru.zarwlad.mdlp.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ShireCaseParserFromEventLines {
    public static void main(String[] args) throws IOException {
        System.out.println("Введи путь к файлу");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        String line = consoleReader.readLine();
        Path jsonFile = Paths.get(line);

        Pattern pattern = Pattern.compile("\"id\": \"(.*)\"");

        String text = Files.readString(jsonFile);

        Set<String> sgtinsIds = new HashSet<>();

        List<String> strings = Files.readAllLines(jsonFile);

        for (String string : strings) {
            String[] splitter = string.split(";");
            if (splitter[5].equals("false")){
                sgtinsIds.add(splitter[3]);
            }
        }

//        Matcher matcher = pattern.matcher(text);
//
//        while (matcher.find()){
//            sgtinsIds.add(matcher.group(1));
//        }
//
//        sgtinsIds.remove("40c7f676-1669-482f-a685-c8f8d4361cb1");

        for (String sgtinsId : sgtinsIds) {
            System.out.println(sgtinsId);
        }
        System.out.println(sgtinsIds.size());

        ObjectMapper objectMapper = new ObjectMapper();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

        Path path = Files.createFile(Paths.get("C:\\Users\\vzaremba\\Desktop\\shire\\"
                + jsonFile.getFileName() + "____"
                + LocalDateTime.now().format(dateTimeFormatter) + ".json"));

        objectMapper.writeValue(new FileWriter(path.toFile()), sgtinsIds);

    }
}
