package ru.zarwlad.mdlp.downloadMdlpPartners.service;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.Application;
import ru.zarwlad.mdlp.downloadMdlpPartners.dao.*;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.LicencesListDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.PharmLicenceDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dtoModelMapper.PharmLicenceMapper;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.RznAddress;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.WorkDescription;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadRznService implements Runnable{
    private static Logger log = LoggerFactory.getLogger(MultiThreadRznService.class);

    private List<PharmLicenceDto> pharmLicenceDtoList;

    public List<PharmLicenceDto> getPharmLicenceDtoList() {
        return pharmLicenceDtoList;
    }

    public void setPharmLicenceDtoList(List<PharmLicenceDto> pharmLicenceDtoList) {
        this.pharmLicenceDtoList = pharmLicenceDtoList;
    }

    @Override
    public void run() {
        MultiThreadCreateModelAndSaveDb(pharmLicenceDtoList);
    }

    static void MultiThreadCreateModelAndSaveDb(List<PharmLicenceDto> pharmLicenceDtoList){
        List<PharmLicence> pharmLicences = new ArrayList<>();

        for (PharmLicenceDto pharmLicenceDto : pharmLicenceDtoList) {
            log.info("Тред {}, обрабатываю лицензию {} из {}",
                    Thread.currentThread().getName(),
                    pharmLicenceDtoList.indexOf(pharmLicenceDto),
                    pharmLicenceDtoList.size());

            PharmLicence pharmLicence = PharmLicenceMapper.fromDtoToEntity(pharmLicenceDto);
            if (pharmLicence != null)
                pharmLicences.add(pharmLicence);
        }

        if (!pharmLicences.isEmpty()){
            try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {

                PharmLicenceJdbcDAO pharmLicenceJdbcDAO = new PharmLicenceJdbcDAO();
                RznAddressJdbcDAO rznAddressJdbcDAO = new RznAddressJdbcDAO();
                FiasJdbcDAO fiasJdbcDAO = new FiasJdbcDAO();
                WorkDescriptionJdbcDAO workDescriptionJdbcDAO = new WorkDescriptionJdbcDAO();

                    for (PharmLicence pharmLicence : pharmLicences) {
                        log.info(pharmLicence.toString());
                        pharmLicenceJdbcDAO.create(pharmLicence);
                        for (RznAddress rznAddress : pharmLicence.getRznAddresses()) {
                            log.info(rznAddress.getFias().toString());
                            fiasJdbcDAO.create(rznAddress.getFias());

                            log.info(rznAddress.toString());
                            rznAddressJdbcDAO.create(rznAddress);
                            for (WorkDescription workDescription : rznAddress.getWorkDescriptions()) {
                                log.info(workDescription.toString());
                                workDescriptionJdbcDAO.create(workDescription);
                            }
                        }
                    }
            }
        }
    }
}
