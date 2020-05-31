package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "rzn_work")
public class WorkDescription {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rzn_address_id")
    @ToString.Exclude
    private RznAddress rznAddress;

    @Column(name = "work_description")
    private String workDescription;
}
