package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import utrace.entities.Event;
import utrace.entities.EventStatus;

import java.util.List;

@JsonAutoDetect
public class AuditRecordDto implements Dto {

    @JsonProperty(value = "author")
    String author;
    @JsonProperty(value = "changeType")
    String changeType;
    @JsonProperty(value = "changedProperties")
    List<String> changedProperties;
    @JsonProperty(value = "operationDateTime")
    String operationDateTime;
    @JsonProperty(value = "snapshot")
    Snapshot snapshot;

    public AuditRecordDto() {
    }

    public AuditRecordDto(String author,
                          String changeType,
                          List<String> changedProperties,
                          String operationDateTime,
                          Snapshot snapshot) {
        this.author = author;
        this.changeType = changeType;
        this.changedProperties = changedProperties;
        this.operationDateTime = operationDateTime;
        this.snapshot = snapshot;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public List<String> getChangedProperties() {
        return changedProperties;
    }

    public void setChangedProperties(List<String> changedProperties) {
        this.changedProperties = changedProperties;
    }

    public String getOperationDateTime() {
        return operationDateTime;
    }

    public void setOperationDateTime(String operationDateTime) {
        this.operationDateTime = operationDateTime;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public Object fromDtoToEntity() {
        if (this instanceof AuditRecordDto) {
            AuditRecordDto auditRecordDto = this;
            EventStatus eventStatus = new EventStatus(snapshot.getStatus(), operationDateTime);
            return eventStatus;
        }
        return null;
    }

    @JsonAutoDetect
    public static class Snapshot {
        String id;
        String status;
        String type;

        public Snapshot() {
        }

        public Snapshot(String id, String status, String type) {
            this.id = id;
            this.status = status;
            this.type = type;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
