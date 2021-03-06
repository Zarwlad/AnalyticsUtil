package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.briefs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class BusinessPartnerBriefDto {
    @CsvBindByName(column = "businessPartnerId")
    String id;

    @CsvBindByName
    String name;
}
