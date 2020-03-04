package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class EventMessageDto implements Dto{
    @JsonProperty(value = "id")
    String id;
    @JsonProperty(value = "direction")
    String direction;
    @JsonProperty(value = "externalSystemId")
    String externalSystemId;
    @JsonProperty(value = "messageId")
    String messageId;
    @JsonProperty(value = "businessEvent")
    EventDto event;

    public EventMessageDto() {
    }

    public EventMessageDto(String id, String direction, String externalSystemId, String messageId, EventDto event) {
        this.id = id;
        this.direction = direction;
        this.externalSystemId = externalSystemId;
        this.messageId = messageId;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(String externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    @Override
    public Object fromDtoToEntity() {
        return null;
    }
}
