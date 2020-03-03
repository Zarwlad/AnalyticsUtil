package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import utrace.entities.Event;

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

    @Override
    public Object fromDtoToEntity() {
        if (this instanceof EventDto){
            EventDto eventDto = (EventDto) this;
            Event event = new Event(eventDto.getId(),
                    eventDto.getType(),
                    eventDto.getOperationDate(),
                    eventDto.getStatus(),
                    eventDto.getRegulatorStatus(),
                    null,
                    null);
            return event;
        }
        return null;
    }
}
