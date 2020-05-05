package ru.zarwlad.utrace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        log.info("Начинаю скачивать события");
        try {
            DownloadEventsService.startDownload();
        } catch (IOException e) {
            log.error(e.toString());
        }

        log.info("Начинаю рассчитывать статистику");
        try {
            EventStatisticCounterService.calculateStatistic();
        }
        catch (Exception e){
            log.error(e.toString());
        }

        log.info("Печатаю статистику");
        try {
            ReportBuilderService.buildMainReport();
            ReportBuilderService.buildTotalReport();
        }
        catch (IOException e){
            log.error(e.toString());
        }
    }

}
