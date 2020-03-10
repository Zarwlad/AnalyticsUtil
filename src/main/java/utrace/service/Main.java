package utrace.service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DownloadEventsService.startDownload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nShow all events started\n\n\n\n\n\n\n\n\n");
        EventTimeCounterService.showAllEvents();
    }

}
