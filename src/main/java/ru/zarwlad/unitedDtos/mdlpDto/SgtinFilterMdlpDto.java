package ru.zarwlad.unitedDtos.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SgtinFilterMdlpDto {
    String batch;

    @JsonProperty(value = "status_date")
    LocalDateTime statusDate;

    @JsonProperty(value = "emission_operation_date")
    LocalDateTime emissionOperationDate;

    @JsonProperty(value = "pack3_id")
    String ssccCase;

    @JsonProperty(value = "last_tracing_op_date")
    LocalDateTime lastTracingOpDate;

    String sgtin;

    String gtin;

    String status;
}
