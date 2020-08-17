package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.util.DateTimeUtil;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

import java.util.UUID;

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

    @JsonAutoDetect
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(of = "id")
    public static class DocumentType{

        @JsonProperty(value = "id")
        private String id;

        @JsonProperty(value = "description")
        private String description;
    }
}
