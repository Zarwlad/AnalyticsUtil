package ru.zarwlad.utrace.model;

import ru.zarwlad.utrace.utraceDto.Dto;

import java.time.ZonedDateTime;
import java.util.Objects;

public class EventStatus implements Entity {
    private String status;
    private ZonedDateTime changeOperationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventStatus)) return false;
        EventStatus that = (EventStatus) o;
        return Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getChangeOperationDate(), that.getChangeOperationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getChangeOperationDate());
    }

    public EventStatus(String status, ZonedDateTime changeOperationDate) {
        this.status = status;
        this.changeOperationDate = changeOperationDate;
    }

    public EventStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getChangeOperationDate() {
        return changeOperationDate;
    }

    public void setChangeOperationDate(ZonedDateTime changeOperationDate) {
        this.changeOperationDate = changeOperationDate;
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return null;
    }
}
