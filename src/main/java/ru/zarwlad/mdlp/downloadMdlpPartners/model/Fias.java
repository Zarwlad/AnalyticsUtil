package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "houseGuid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Fias {
    private UUID aoGuid;
    private UUID houseGuid;
}
