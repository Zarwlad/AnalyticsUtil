package ru.zarwlad.utrace.unitedDtos.utraceDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EventLinePostDto {
    String code;

    @JsonProperty(value = "isSscc")
    boolean sscc;

    Object parent;
}
