package utrace.service;

import com.ctc.wstx.shaded.msv_core.reader.ChoiceState;
import utrace.data.EventStatsData;
import utrace.entities.EventStatistic;

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
    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(new File("C:\\Users\\Vladimir\\IdeaProjects\\AnalyticsUtil\\src\\main\\java\\utrace\\service\\app.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void buildReport() throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

        Path path = Files.createFile(Paths.get("C:\\Users\\Vladimir\\Desktop\\reports\\"
                + properties.getProperty("client") + "____"
                + LocalDateTime.now().format(dateTimeFormatter) + ".csv"));

        Files.writeString(path,
                "event_id;"
                + "event_type;"
                + "event_posting_seconds;"
                + "messages_send_seconds;"
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
                            + eventStatistic.getMessagesSendSeconds() + ";"
                            + eventStatistic.getErrorEvent() + ";"
                            + eventStatistic.getErrorMessage() + ";"
                            + eventStatistic.getEventPosted() + ";"
                            + eventStatistic.getMessageCreated() + ";"
                            + eventStatistic.getEventMonth() + ";" + "\n",
                    StandardOpenOption.APPEND);
        }
    }
}
