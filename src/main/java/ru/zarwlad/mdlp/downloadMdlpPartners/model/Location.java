package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "branchId")
@ToString
@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "branch_id")
    private String branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fias_id")
    private Fias fias;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_status")
    private LocationStatus locationStatus;

    @Column(name = "is_address_found_in_fias")
    private boolean addressFoundInFias;

    @ManyToOne
    @JoinColumn(name = "business_partner_id")
    private BusinessPartner businessPartner;

    @Column(name = "is_safe_warehouse")
    private boolean safeWarehouse;

}
