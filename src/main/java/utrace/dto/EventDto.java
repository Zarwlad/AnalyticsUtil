package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import utrace.entities.Event;

import java.time.ZonedDateTime;

@JsonAutoDetect
public class EventDto implements Dto{
    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "type")
    String type;

    @JsonProperty(value = "operationDate")
    String operationDate;

    @JsonProperty(value = "status")
    String status;

    @JsonProperty(value = "regulatorStatus")
    String regulatorStatus;

    @JsonProperty(value = "created")
    String created;

    public EventDto() {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public Object fromDtoToEntity() {
        EventDto eventDto = (EventDto) this;
        return new Event(eventDto.getId(),
                eventDto.getType(),
                ZonedDateTime.parse(eventDto.getOperationDate()),
                eventDto.getStatus(),
                eventDto.getRegulatorStatus(),
                null,
                null,
                ZonedDateTime.parse(eventDto.getCreated()));
    }
}
