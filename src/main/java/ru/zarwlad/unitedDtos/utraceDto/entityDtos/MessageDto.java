package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
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
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(of = "id")
    static class DocumentType{

        @JsonProperty(value = "id")
        String id;

        @JsonProperty(value = "description")
        String description;
    }
}
