package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "licence")
//, uniqueConstraints =
//@UniqueConstraint(columnNames = {"licence_number", "licence_date"})
public class PharmLicence {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "business_partner_id")
    private BusinessPartner businessPartner;

    @Column(name = "licence_number")
    private String licenceNumber;

    @Column(name = "licence_date")
    private LocalDate licenceDate;

    @Column(name = "is_terminated")
    private boolean terminated;

    @Column(name = "activity_type")
    private String activityType;

    @OneToMany(mappedBy = "pharmLicence")
    private List<RznAddress> rznAddresses;

}
