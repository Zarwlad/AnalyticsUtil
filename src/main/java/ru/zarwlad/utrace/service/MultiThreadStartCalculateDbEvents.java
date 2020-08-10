package ru.zarwlad.utrace.service;

public class MultiThreadStartCalculateDbEvents implements Runnable {
    @Override
    public void run() {
        EventStatsDbCounter.calculateStats();
    }
}
