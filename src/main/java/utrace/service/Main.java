package utrace.service;

import utrace.data.AuthData;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(AuthData.getInstance().getAuth().getAccessToken());

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
            ReportBuilderService.buildMainReport();
            ReportBuilderService.buildTotalReport();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
