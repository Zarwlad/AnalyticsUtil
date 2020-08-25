package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs;

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
public class LegalEntityBriefDto {
    @CsvBindByName(column = "legalEntitId")
    String id;

    @JsonProperty(value = "businessPartner")
    @CsvRecurse
    BusinessPartnerBriefDto businessPartnerBriefDto;
}
