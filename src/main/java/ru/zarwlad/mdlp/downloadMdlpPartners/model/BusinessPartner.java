package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "systemSubjId")
public class BusinessPartner {
    private UUID systemSubjId;
    private String inn;
    private String kpp;
    private String orgName;
    private String ogrn;
    private String dirFirstName;
    private String dirMiddleName;
    private String dirLastName;
    private ZonedDateTime requestRegDate; //дата заявки на регистрацию, в апи мдлп - op_date
    private ZonedDateTime factReqDate; //дата фактической регистрации
    private String countryCode;
    private int federalSubjCode;
    private boolean stateGovSupplier;
    private EntityType entityType;

    private enum EntityType{
        RESIDENT(1),
        NON_RES_AGENCY(2),
        NON_RESIDENT(3);

        private int type;

        EntityType(int type){
            this.type = type;
        }

        @Override
        public String toString() {
            return "EntityType{" +
                    "type=" + type +
                    '}';
        }
    }

}
