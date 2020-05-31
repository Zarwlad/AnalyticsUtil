package ru.zarwlad.utrace.utraceDto.entityDtos.briefs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class LegalEntityBriefDto {
    String id;

    @JsonProperty(value = "businessPartner")
    BusinessPartnerBriefDto businessPartnerBriefDto;
}
