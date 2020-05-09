package ru.zarwlad.mdlp.downloadMdlpPartners.dtoModelMapper;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.BusinessPartnerDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.LocationShortDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class BusinessPartnerMapper  {

    public static BusinessPartner fromDtoToEntity(BusinessPartnerDto businessPartnerDto){

        EntityType entityType = null;
        switch (businessPartnerDto.getEntityType()){
            case 1: entityType = EntityType.RESIDENT;
                break;
            case 2: entityType = EntityType.NON_RES_AGENCY;
                break;
            case 3: entityType = EntityType.NON_RESIDENT;
        }


        BusinessPartner businessPartner = new BusinessPartner(
                UUID.fromString(businessPartnerDto.getSystemSubjId()),
                businessPartnerDto.getInn(),
                businessPartnerDto.getKpp(),
                businessPartnerDto.getOrgName(),
                businessPartnerDto.getOgrn(),
                businessPartnerDto.getDirFirstName(),
                businessPartnerDto.getDirMiddleName(),
                businessPartnerDto.getDirLastName(),
                ZonedDateTime.parse(businessPartnerDto.getRequestRegDate().getRequestRegDate()),
                ZonedDateTime.parse(businessPartnerDto.getFactReqDate()),
                businessPartnerDto.getCountryCode(),
                businessPartnerDto.getFederalSubjCode(),
                businessPartnerDto.isStateGovSupplier(),
                entityType,
                new HashSet<>()
        );

        List<Location> branches = locations(businessPartner, businessPartnerDto.getBranches(), false);
        List<Location> safeWh = locations(businessPartner, businessPartnerDto.getSafeWarehouses(), true);
        businessPartner.getLocations().addAll(branches);
        businessPartner.getLocations().addAll(safeWh);

        return businessPartner;
    }

    public static List<Location> locations(BusinessPartner businessPartner,
                                           List<LocationShortDto> locationShortDtos,
                                           boolean isSafeWh) {
        List<Location> locations = new ArrayList<>();

        for (LocationShortDto locationShortDto : locationShortDtos) {
            Fias fias = new Fias(
                    UUID.fromString(locationShortDto.getAddressFiasShortDto().getAoguid()),
                    UUID.fromString(locationShortDto.getAddressFiasShortDto().getHouseguid())
            );

            boolean isAddressFoundInFias = false;
            if (locationShortDto.getAddressResolvedShortDto().getCode() == 0)
                isAddressFoundInFias = true;

            LocationStatus locationStatus = null;
            switch (locationShortDto.getStatus()){
                case 0: locationStatus = LocationStatus.NOT_ACTIVE;
                    break;
                case 1: locationStatus = LocationStatus.ACTIVE;
                    break;
                case 2: locationStatus = LocationStatus.DEACTIVATION;
            }

            Location location = new Location(
                    locationShortDto.getId(),
                    fias,
                    locationShortDto.getAddressResolvedShortDto().getAddress(),
                    locationStatus,
                    isAddressFoundInFias,
                    businessPartner,
                    isSafeWh
            );
            locations.add(location);
        }
        return locations;
    }
}
