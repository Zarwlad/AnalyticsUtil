package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.BusinessEventBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.BusinessPartnerBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.LegalEntityBriefDto;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs.LocationBriefDto;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UnitUnpackEventPostDto {
    BusinessPartnerBriefDto businessPartner;

    BusinessEventBriefDto correctEvent;

    Boolean correction;

    String externalOperationId;

    String groupId;

    @JsonProperty(value = "isArtificialEvent")
    Boolean artificialEvent;

    @JsonProperty(value = "isManualCreated")
    Boolean manualCreated;

    @JsonProperty(value = "isReadyToProcess")
    Boolean readyToProcess;

    LegalEntityBriefDto legalEntity;

    LocationBriefDto location;

    String operationDate;

    Integer priority;

    Boolean recursive;

    String regulatorStatus;

    Boolean setCurrentOperationDate;

    Boolean storno;
}
