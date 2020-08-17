package ru.zarwlad.mdlp.downloadMdlpPartners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.rznDto.LicencesListDto;
import ru.zarwlad.unitedDtos.rznDto.PharmLicenceDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.service.MultiThreadRznService;
import ru.zarwlad.mdlp.downloadMdlpPartners.service.RznParserService;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //List<BusinessPartner> businessPartners = DownloadMdlpService.downloadBusinessPartners();

        LicencesListDto licencesListDto = RznParserService.startParsing();

        List<PharmLicenceDto> pharmLicenceDtoList = new ArrayList<>(licencesListDto.getPharmLicenceDtoList());

        List<List<PharmLicenceDto>> pharmLicenceBatchList = new ArrayList<>();
        List<PharmLicenceDto> pharmLicencePart = new ArrayList<>();

        for (PharmLicenceDto pharmLicenceDto : pharmLicenceDtoList) {
            pharmLicencePart.add(pharmLicenceDto);

            if (pharmLicencePart.size() == 5000){
                log.info("Создаю новый батч pharmLicenceDto, всего батчей {}", pharmLicenceBatchList.size());

                pharmLicenceBatchList.add(pharmLicencePart);
                pharmLicencePart = new ArrayList<>();
            }
        }
        if (pharmLicencePart.size() > 0)
            pharmLicenceBatchList.add(pharmLicencePart);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < pharmLicenceBatchList.size(); i++) {
            MultiThreadRznService multiThreadRznService = new MultiThreadRznService();
            multiThreadRznService.setPharmLicenceDtoList(pharmLicenceBatchList.get(i));

            Thread thread = new Thread(multiThreadRznService);
            threads.add(thread);
        }

        log.info("Создано тредов: {}", threads.size());

        for (Thread thread : threads) {
            thread.start();
        }

//        List<PharmLicence> pharmLicences = new ArrayList<>();
//
//        for (PharmLicenceDto pharmLicenceDto : licencesListDto.getPharmLicenceDtoList()) {
//            PharmLicence pharmLicence = PharmLicenceMapper.fromDtoToEntity(pharmLicenceDto);
//            if (pharmLicence != null)
//                pharmLicences.add(pharmLicence);
//        }
//
//        if (!pharmLicences.isEmpty()){
//            try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {
//                PharmLicenceDAO pharmLicenceDAO = new PharmLicenceDAO(sessionFactory);
//                RznAddressDAO rznAddressDAO = new RznAddressDAO(sessionFactory);
//                WorkDescriptionDAO workDescriptionDAO = new WorkDescriptionDAO(sessionFactory);
//                FiasDAO fiasDAO = new FiasDAO(nFactory);
//
//                for (PharmLicence pharmLicence : pharmLicences) {
//                    pharmLicenceDAO.create(pharmLicence);
//                    for (RznAddress rznAddress : pharmLicence.getRznAddresses()) {
//                        fiasDAO.create(rznAddress.getFias());
//                        rznAddressDAO.create(rznAddress);
//                        for (WorkDescription workDescription : rznAddress.getWorkDescriptions()) {
//                            workDescriptionDAO.create(workDescription);
//                        }
//                    }
//                }
//            }
//        }
    }
}
