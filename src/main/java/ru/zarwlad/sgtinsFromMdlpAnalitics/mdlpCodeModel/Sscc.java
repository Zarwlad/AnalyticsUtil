package ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sscc")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "sscc")
public class Sscc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "sscc")
    String sscc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_sscc_id")
    Sscc parentSscc;
}
