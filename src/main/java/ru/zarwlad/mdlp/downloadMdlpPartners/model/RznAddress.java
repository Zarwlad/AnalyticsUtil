package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "rzn_address")
//, uniqueConstraints =
//@UniqueConstraint(columnNames = {"fias_id", "licence_id"})
public class RznAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fias_id")
    private Fias fias;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "licence_id")
    @ToString.Exclude
    private PharmLicence pharmLicence;

    @Column(name = "rzn_address")
    private String rznAddress;

    @OneToMany(mappedBy = "rznAddress")
    private Set<WorkDescription> workDescriptions;
}
