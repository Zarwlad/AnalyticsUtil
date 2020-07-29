package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvRecurse;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.BusinessPartnerBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.CodeBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.LegalEntityBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.LocationBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CodeTreeRootDto implements Dto {
    @CsvBindByName(column = "codeTreeRootId")
    String id;

    @CsvBindByName
    String acceptStatus;

    @CsvBindByName
    String baseStatus;

    @CsvBindByName
    String regulatorStatus;

    @CsvBindByName
    boolean reserved;

    @JsonProperty(value = "legalEntity")
    @CsvRecurse
    LegalEntityBriefDto legalEntityBriefDto;

    @JsonProperty(value = "location")
    @CsvRecurse
    LocationBriefDto locationBriefDto;

    @JsonProperty(value = "owner")
    @CsvRecurse
    BusinessPartnerBriefDto ownerBpBriefDto;

    @JsonProperty(value = "receiverBp")
    @CsvRecurse
    BusinessPartnerBriefDto receiverBpBriefDto;

    @JsonProperty(value = "receiverLocation")
    @CsvRecurse
    LocationBriefDto receiverLocBriefDto;

    @JsonProperty(value = "code")
    @CsvRecurse
    CodeBriefDto codeBriefDto;

}
