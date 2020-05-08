package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "branchId")
public class Location {
    private String branchId;
    private Fias fias;
    private String address;
    private LocationStatus locationStatus;
    private boolean addressFoundInFias;
    private BusinessPartner businessPartner;
    private boolean safeWarehouse;

    private enum LocationStatus{
        NOT_ACTIVE(0),
        ACTIVE(1),
        DEACTIVATION(2);

        private int status;

        LocationStatus(int status){
            this.status = status;
        }
    }
}
