package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.Dto;

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

    @JsonProperty(value = "filename")
    String filename;

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
