package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "systemSubjId")
@ToString
@Entity
@Table(name = "business_partner")
public class BusinessPartner {
    @Id
    @Column(name = "system_subj_id")
    private UUID systemSubjId;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "dir_first_name")
    private String dirFirstName;

    @Column(name = "dir_middle_name")
    private String dirMiddleName;

    @Column(name = "dir_last_name")
    private String dirLastName;

    @Column(name = "request_reg_date")
    private ZonedDateTime requestRegDate; //дата заявки на регистрацию, в апи мдлп - op_date

    @Column(name = "fact_req_date")
    private ZonedDateTime factReqDate; //дата фактической регистрации

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "federal_subj_code")
    private int federalSubjCode;

    @Column(name = "state_gov_supplier")
    private boolean stateGovSupplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private EntityType entityType;


    @ToString.Exclude
    @OneToMany(mappedBy = "businessPartner", fetch = FetchType.LAZY)
    private Set<Location> locations;

}
