package utrace.entities;

import utrace.dto.Dto;
import utrace.dto.MessageDto;

import java.util.Objects;

public class MessageHistory implements Entity {
    String id;
    String authoredBy;
    String created;
    String status;

    public MessageHistory() {
    }

    public MessageHistory(String id, String authoredBy, String created, String status) {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
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
