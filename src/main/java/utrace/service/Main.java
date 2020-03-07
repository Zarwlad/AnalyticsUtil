package utrace.service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DownloadEventsService.startDownload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
