package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class PharmLicence {
    private UUID id;
    private BusinessPartner businessPartner;
    private String licenceNumber;
    private LocalDate licenceDate;
    private boolean terminated;
}
