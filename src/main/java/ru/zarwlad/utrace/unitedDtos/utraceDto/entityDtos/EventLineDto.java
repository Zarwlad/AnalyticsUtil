package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvRecurse;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class EventLineDto implements Dto {
    @JsonProperty(value = "id")
    @CsvBindByName(column = "event_line_id")
    String id;

    @JsonProperty(value = "event")
    @CsvIgnore
    EventDto eventDto;

    @JsonProperty(value = "code")
    @CsvBindByName(column = "event_line_code")
    String code;

    @JsonProperty(value = "isSscc")
    @CsvBindByName(column = "is_sscc")
    boolean sscc;

    @JsonProperty(value = "parent")
    @CsvIgnore
    EventLineDto parentLineDto;
}
