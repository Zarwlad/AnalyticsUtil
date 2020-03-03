package utrace.entities;

import utrace.dto.Dto;
import utrace.dto.EventDto;

import java.util.Objects;
import java.util.Set;

public class Event implements Entity{
    private String id;
    private String type;
    private String operationDate;
    private String status;
    private String regulatorStatus;
    private Set<EventStatus> eventStatuses;
    private Set<Message> messages;

    public Event() {
    }

    public Event(String id, String type, String operationDate, String status, String regulatorStatus, Set<EventStatus> eventStatuses, Set<Message> messages) {
        this.id = id;
        this.type = type;
        this.operationDate = operationDate;
        this.status = status;
        this.regulatorStatus = regulatorStatus;
        this.eventStatuses = eventStatuses;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public Set<EventStatus> getEventStatuses() {
        return eventStatuses;
    }

    public void setEventStatuses(Set<EventStatus> eventStatuses) {
        this.eventStatuses = eventStatuses;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegulatorStatus() {
        return regulatorStatus;
    }

    public void setRegulatorStatus(String regulatorStatus) {
        this.regulatorStatus = regulatorStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return getId().equals(event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return new EventDto();
    }
}
