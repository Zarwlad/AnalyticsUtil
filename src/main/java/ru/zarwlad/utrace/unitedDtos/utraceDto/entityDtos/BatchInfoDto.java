package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.TradeItemBriefDto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BatchInfoDto {
    private String batchOrLot;
    private String dateExp;
    private String dateMdf;
    private String id;

    @JsonProperty(value = "tradeItem")
    private TradeItemBriefDto tradeItemBriefDto;
}
