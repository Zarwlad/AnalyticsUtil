package ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class BatchBriefDto {
    @CsvBindByName
    String batchOrLot;

    @CsvBindByName(column = "batchId")
    String id;

    @JsonProperty(value = "tradeItem")
    @CsvRecurse
    TradeItemBriefDto tradeItemBriefDto;
}
