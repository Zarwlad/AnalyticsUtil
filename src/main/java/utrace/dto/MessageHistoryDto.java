package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import utrace.entities.MessageHistory;

@JsonAutoDetect
public class MessageHistoryDto implements Dto {

    @JsonProperty(value = "authoredBy")
    String authoredBy;

    @JsonProperty(value = "created")
    String created;

    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "message")
    MessageDto messageDto;

    @JsonProperty(value = "status")
    String status;

    public MessageHistoryDto() {
    }

    public MessageHistoryDto(String authoredBy, String created, String id, MessageDto messageDto, String status) {
        this.authoredBy = authoredBy;
        this.created = created;
        this.id = id;
        this.messageDto = messageDto;
        this.status = status;
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

    public MessageDto getMessageDto() {
        return messageDto;
    }

    public void setMessageDto(MessageDto messageDto) {
        this.messageDto = messageDto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Object fromDtoToEntity() {
        MessageHistoryDto messageHistoryDto = (MessageHistoryDto) this;
        return new MessageHistory(messageHistoryDto.getId(),
                messageHistoryDto.getAuthoredBy(),
                messageHistoryDto.getCreated(),
                messageHistoryDto.getStatus());
    }
}
