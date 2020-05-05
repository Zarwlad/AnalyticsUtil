package ru.zarwlad.utrace.model;

import ru.zarwlad.utrace.utraceDto.Dto;

import java.time.ZonedDateTime;
import java.util.Objects;

public class MessageHistory implements Entity {
    private String id;
    private String authoredBy;
    private ZonedDateTime created;
    private String status;

    public MessageHistory() {
    }

    public MessageHistory(String id, String authoredBy, ZonedDateTime created, String status) {
        this.id = id;
        this.authoredBy = authoredBy;
        this.created = created;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageHistory)) return false;
        MessageHistory that = (MessageHistory) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getAuthoredBy() {
        return authoredBy;
    }

    public void setAuthoredBy(String authoredBy) {
        this.authoredBy = authoredBy;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return null;
    }
}
