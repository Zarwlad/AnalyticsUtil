package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.Dto;

import java.util.List;
import java.util.UUID;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class AuditRecordDto implements Dto {
    private UUID id = UUID.randomUUID();

    @JsonProperty(value = "author")
    private String author;

    @JsonProperty(value = "changeType")
    private String changeType;

    @JsonProperty(value = "changedProperties")
    private List<String> changedProperties;

    @JsonProperty(value = "operationDateTime")
    private String operationDateTime;

    @JsonProperty(value = "snapshot")
    private Snapshot snapshot;

    @JsonAutoDetect
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(of = "id")
    public static class Snapshot {
        String id;

        String status;

        String type;
    }
}
