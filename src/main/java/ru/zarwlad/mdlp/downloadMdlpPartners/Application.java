package ru.zarwlad.mdlp.downloadMdlpPartners;

import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.LicencesListDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dtoModelMapper.PharmLicenceMapper;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;
import ru.zarwlad.mdlp.downloadMdlpPartners.service.RznParserService;

public class Application {

    public static void main(String[] args) {
        //List<BusinessPartner> businessPartners = DownloadMdlpService.downloadBusinessPartners();

        LicencesListDto licencesListDto = RznParserService.startParsing();

        PharmLicence pharmLicence = PharmLicenceMapper.fromDtoToEntity(licencesListDto.getPharmLicenceDtoList().get(0));
    }
}
