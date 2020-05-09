package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class RznAddress {
    private UUID id;
    private Fias fias;
    private PharmLicence pharmLicence;
    private String rznAddress;
}
