package ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "sgtin")
@Table(name = "sgtin")
public class Sgtin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String sgtin;

    String status;

    @Column(name = "batch_or_lot")
    String batch;

    String gtin;

    @Column(name = "last_status_op_date")
    LocalDateTime lastStatusOpDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sscc_id")
    Sscc sscc;

    @Column(name = "emission_operation_date")
    LocalDateTime emissionOperationDate;

    @Column(name = "status_date")
    LocalDateTime statusDate;
}
