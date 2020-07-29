package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@EqualsAndHashCode(of = "houseGuid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "fias")
public class Fias {
    @Id
    @Column(name = "house_guid")
    private UUID houseGuid;

    @Column(name = "ao_guid")
    private UUID aoGuid;
}
