package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.data.EventStatsData;
import ru.zarwlad.utrace.model.AverageCount;
import ru.zarwlad.utrace.model.EventStatistic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportBuilderService {
    private static Logger log = LoggerFactory.getLogger(ReportBuilderService.class);

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("src\\main\\java\\ru.zarwlad.utrace\\service\\app.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");


    public static void buildMainReport() throws IOException {
        Path path = Files.createFile(Paths.get("src\\reports\\"
                + properties.getProperty("client") + "____"
                + LocalDateTime.now().format(dateTimeFormatter) + ".csv"));

        Files.writeString(path,
                "event_id;"
                + "event_type;"
                + "event_posting_seconds;"
                + "messages_send_seconds_avg;"
                + "total_sending_seconds;"
                + "is_error_event;"
                + "is_error_message;"
                + "is_event_posted;"
                + "is_message_created;"
                + "event_month;\n");

        EventStatsData eventStatsData = EventStatsData.getInstance();

        for (EventStatistic eventStatistic : eventStatsData.getEventStatistics()) {
            Files.writeString(path,
                    eventStatistic.getEvent().getId() + ";"
                            + eventStatistic.getEvent().getType() + ";"
                            + eventStatistic.getEventPostingSeconds() + ";"
                            + eventStatistic.getMessagesSendSecondsAvg() + ";"
                            + eventStatistic.getTotalSendingSeconds() + ";"
                            + eventStatistic.getErrorEvent() + ";"
                            + eventStatistic.getErrorMessage() + ";"
                            + eventStatistic.getEventPosted() + ";"
                            + eventStatistic.getMessageCreated() + ";"
                            + eventStatistic.getEventMonth() + ";" + "\n",
                    StandardOpenOption.APPEND);
        }
    }

    public static void buildTotalReport() throws IOException {
        Path path = Files.createFile(Paths.get("src\\reports\\"
                + properties.getProperty("client") + "__total__"
                + LocalDateTime.now().format(dateTimeFormatter) + ".csv"));

        AverageCount averageCount = EventStatisticCounterService.calculateAverageForMonth();

        Files.writeString(path,
                    "month;"
                        + "total_events_was_sended;"
                        + "total_seconds;"
                        + "average_count_sec;"
                        + "average_count_hours;"
                        + "\n");

        long hour = averageCount.getAverageTime().intValue() / 3600;
        long min = averageCount.getAverageTime().intValue() / 60 % 60;
        long sec = averageCount.getAverageTime().intValue() % 60;

        Files.writeString(path,
                properties.getProperty("targetMonth") + ";"
                + averageCount.getTotalEvents() + ";"
                + averageCount.getTotalSeconds() + ";"
                + averageCount.getAverageTime() + ";"
                + String.format("%02d:%02d:%02d", hour, min, sec) + ";"
                + "\n",
                StandardOpenOption.APPEND);
    }
}
