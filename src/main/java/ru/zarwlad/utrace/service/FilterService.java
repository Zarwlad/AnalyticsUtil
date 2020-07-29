package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FilterService {
    private static Logger log = LoggerFactory.getLogger(FilterService.class);

    public static List<String> readIdsFromFile() throws IOException {
        log.info("Введи путь к файлу");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> strings = Files.readAllLines(Paths.get(bufferedReader.readLine()));

        List<String> normalizedStrings = new ArrayList<>();

        for (String string : strings) {
            normalizedStrings.add(string.trim());
        }

        return normalizedStrings;
    }
}
