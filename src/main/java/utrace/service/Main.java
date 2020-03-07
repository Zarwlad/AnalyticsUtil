package utrace.service;

import utrace.util.DateParser;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            DownloadEventsService.startDownload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
