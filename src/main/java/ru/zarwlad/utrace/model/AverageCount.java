package ru.zarwlad.utrace.model;

import java.math.BigDecimal;

public class AverageCount {
    BigDecimal totalSeconds;
    Integer totalEvents;
    BigDecimal averageTime;

    public AverageCount() {
    }

    public AverageCount(BigDecimal totalSeconds, Integer totalEvents, BigDecimal averageTime) {
        this.totalSeconds = totalSeconds;
        this.totalEvents = totalEvents;
        this.averageTime = averageTime;
    }

    public BigDecimal getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(BigDecimal totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

    public BigDecimal getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(BigDecimal averageTime) {
        this.averageTime = averageTime;
    }

}
