package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReaderService {
    private static final Logger log = LoggerFactory.getLogger(FileReaderService.class);

    public static List<String> readIdsFromFile() throws IOException {
        log.info("Введи путь к файлу");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> strings = Files.readAllLines(Paths.get(bufferedReader.readLine()));

        return strings.stream().map(String::trim).collect(Collectors.toList());
    }
}
