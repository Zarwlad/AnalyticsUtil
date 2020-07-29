package ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
public class TradeItemBriefDto {
    @CsvBindByName(column = "tradeItemExternalId")
    String externalId;

    @CsvBindByName(column = "tradeItemId")
    String id;

    @CsvBindByName
    String gtin;

    @CsvBindByName
    String name;
}
