package utrace.service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Начинаю скачивать события!");
        try {
            DownloadEventsService.startDownload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Начинаю рассчитывать статистику!");
        try {
            EventStatisticCounterService.calculateStatistic();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Печатаю статистику!");
        try {
            ReportBuilderService.buildReport();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
