package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "branchId")
@ToString
public class Location {
    private String branchId;
    private Fias fias;
    private String address;
    private LocationStatus locationStatus;
    private boolean addressFoundInFias;
    private BusinessPartner businessPartner;
    private boolean safeWarehouse;

}
