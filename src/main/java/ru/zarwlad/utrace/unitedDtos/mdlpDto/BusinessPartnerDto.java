package ru.zarwlad.utrace.unitedDtos.mdlpDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "systemSubjId")
public class BusinessPartnerDto {
    @JsonProperty(value = "system_subj_id")
    private String systemSubjId;

    @JsonProperty(value = "inn")
    private String inn;

    @JsonProperty(value = "KPP")
    private String kpp;

    @JsonProperty(value = "ORG_NAME")
    private String orgName;

    @JsonProperty(value = "OGRN")
    private String ogrn;

    @JsonProperty(value = "FIRST_NAME")
    private String dirFirstName;

    @JsonProperty(value = "MIDDLE_NAME")
    private String dirMiddleName;

    @JsonProperty(value = "LAST_NAME")
    private String dirLastName;

    @JsonProperty(value = "op_date")
    private RequestRegDateDto requestRegDate;

    @JsonProperty(value = "op_exec_date")
    private String factReqDate;

    @JsonProperty(value = "country_code")
    private String countryCode;

    @JsonProperty(value = "federal_subject_code")
    private int federalSubjCode;

    @JsonProperty(value = "state_gov_supplier")
    private boolean stateGovSupplier;

    @JsonProperty(value = "entity_type")
    private int entityType;

    @JsonProperty(value = "branches")
    private List<LocationShortDto> branches;

    @JsonProperty(value = "safe_warehouses")
    private List<LocationShortDto> safeWarehouses;

    @JsonAutoDetect
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    public class RequestRegDateDto{
        @JsonProperty(value = "$date")
        private String requestRegDate;
    }
}
