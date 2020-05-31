package ru.zarwlad.utrace.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.utraceDto.Dto;
import ru.zarwlad.utrace.utraceDto.entityDtos.briefs.BusinessPartnerBriefDto;
import ru.zarwlad.utrace.utraceDto.entityDtos.briefs.LegalEntityBriefDto;
import ru.zarwlad.utrace.utraceDto.entityDtos.briefs.LocationBriefDto;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CodeTreeRootDto implements Dto {
    String acceptStatus;

    String baseStatus;

    String regulatorStatus;

    boolean reserved;

    @JsonProperty(value = "legalEntity")
    LegalEntityBriefDto legalEntityBriefDto;

    @JsonProperty(value = "location")
    LocationBriefDto locationBriefDto;

    @JsonProperty(value = "owner")
    BusinessPartnerBriefDto ownerBpBriefDto;

    @JsonProperty(value = "receiverBp")
    BusinessPartnerBriefDto receiverBpBriefDto;

    @JsonProperty(value = "receiverLocation")
    LocationBriefDto receiverLocBriefDto;
}
