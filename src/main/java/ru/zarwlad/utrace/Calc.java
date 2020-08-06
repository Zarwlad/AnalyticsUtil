package ru.zarwlad.utrace;

import ru.zarwlad.utrace.service.EventStatsDbCounter;

public class Calc {
    public static void main(String[] args) {
        EventStatsDbCounter.calculateStats();
    }
}
