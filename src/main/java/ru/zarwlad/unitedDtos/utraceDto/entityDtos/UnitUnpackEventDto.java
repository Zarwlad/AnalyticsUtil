package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.BusinessEventBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.BusinessPartnerBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.LegalEntityBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.LocationBriefDto;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UnitUnpackEventDto {
    String id;

    String created;

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
