package ru.zarwlad.utrace.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.utrace.utraceDto.Dto;

@JsonAutoDetect
public class MessageDto implements Dto {

    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "status")
    String status;

    @JsonProperty(value = "created")
    String created;

    @JsonProperty(value = "operationDate")
    String operationDate;

    @JsonProperty(value = "headers")
    String headers;

    @JsonProperty(value = "documentType")
    DocumentType documentType;


    public MessageDto() {
    }

    public MessageDto(String id, String status, String created, String operationDate, String headers, DocumentType documentType) {
        this.id = id;
        this.status = status;
        this.created = created;
        this.operationDate = operationDate;
        this.headers = headers;
        this.documentType = documentType;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Message fromDtoToEntity() {
        MessageDto messageDto = (MessageDto) this;
        return new Message(messageDto.getId(),
                messageDto.getStatus(),
                messageDto.getDocumentType().getId(),
                DateTimeUtil.toZonedDateTime(messageDto.getCreated()),
                DateTimeUtil.toZonedDateTime(messageDto.getOperationDate()),
                null);
    }

    @JsonAutoDetect
    static class DocumentType{

        @JsonProperty(value = "id")
        String id;

        @JsonProperty(value = "description")
        String description;

        public DocumentType() {
        }

        public DocumentType(String id, String description) {
            this.id = id;
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
