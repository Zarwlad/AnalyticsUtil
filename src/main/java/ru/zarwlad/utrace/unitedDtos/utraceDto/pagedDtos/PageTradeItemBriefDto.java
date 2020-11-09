package ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.BatchInfoDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.TradeItemBriefDto;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageTradeItemBriefDto {
    @JsonProperty(value = "data")
    List<TradeItemBriefDto> tradeItemBriefDtos;

    @JsonProperty(value = "page")
    Page page;
}
