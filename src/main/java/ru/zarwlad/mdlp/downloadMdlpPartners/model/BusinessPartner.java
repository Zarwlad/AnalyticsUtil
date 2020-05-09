package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "systemSubjId")
@ToString
public class BusinessPartner {
    private UUID systemSubjId;
    private String inn;
    private String kpp;
    private String orgName;
    private String ogrn;
    private String dirFirstName;
    private String dirMiddleName;
    private String dirLastName;
    private ZonedDateTime requestRegDate; //дата заявки на регистрацию, в апи мдлп - op_date
    private ZonedDateTime factReqDate; //дата фактической регистрации
    private String countryCode;
    private int federalSubjCode;
    private boolean stateGovSupplier;
    private EntityType entityType;
    @ToString.Exclude
    private Set<Location> locations;

}
