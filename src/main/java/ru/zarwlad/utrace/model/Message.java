package ru.zarwlad.utrace.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

public class Message {
    private String id;
    private String status;
    private String documentTypeId;
    private ZonedDateTime createdDate;
    private ZonedDateTime operationDate;

    Set<MessageHistory> messageHistories;

    public Message() {
    }

    public Message(String id,
                   String status,
                   String documentTypeId,
                   ZonedDateTime createdDate,
                   ZonedDateTime operationDate,
                   Set<MessageHistory> messageHistories) {
        this.id = id;
        this.status = status;
        this.documentTypeId = documentTypeId;
        this.createdDate = createdDate;
        this.operationDate = operationDate;
        this.messageHistories = messageHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId().equals(message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(ZonedDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public Set<MessageHistory> getMessageHistories() {
        return messageHistories;
    }

    public void setMessageHistories(Set<MessageHistory> messageHistories) {
        this.messageHistories = messageHistories;
    }
}
