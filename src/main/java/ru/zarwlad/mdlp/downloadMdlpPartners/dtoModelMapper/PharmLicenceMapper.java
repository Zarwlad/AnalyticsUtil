package ru.zarwlad.mdlp.downloadMdlpPartners.dtoModelMapper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.PharmLicenceDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.RznAddressDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.WorkDescriptionDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PharmLicenceMapper {
    private static Logger log = LoggerFactory.getLogger(PharmLicenceMapper.class);

    public static PharmLicence fromDtoToEntity(PharmLicenceDto pharmLicenceDto){
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()){
            log.info("Обрабатываю лицензию {}", pharmLicenceDto.getNumber());

            BusinessPartner businessPartner = null;
            try (Session session = sessionFactory.openSession()) {
                CriteriaBuilder builder = sessionFactory.openSession().getCriteriaBuilder();
                CriteriaQuery<BusinessPartner> cq = builder.createQuery(BusinessPartner.class);
                Root<BusinessPartner> root = cq.from(BusinessPartner.class);
                cq.select(root).where(builder.equal(root.get("inn"), pharmLicenceDto.getInn()));

                Query<BusinessPartner> query = session.createQuery(cq);

                List<BusinessPartner> results = query.getResultList();

                for (BusinessPartner result : results) {
                    businessPartner = result;
                }
                session.close();
            }

            boolean isTerminated = false;
            if (!pharmLicenceDto.getTermination().isEmpty())
                isTerminated = true;

            if (businessPartner == null || isTerminated) {
                if (businessPartner == null)
                    log.info("Не найден бизнес-партнер для лицензии {}", pharmLicenceDto);
                else
                    log.info("Лицензия отозвана {}", pharmLicenceDto);
                return null;
            } else {
                PharmLicence pharmLicence = new PharmLicence();

                List<RznAddress> rznAddresses = getRznAddresses(pharmLicenceDto, pharmLicence);
                if (rznAddresses.isEmpty()) {
                    log.info("Для лицензии {} не указаны адреса", pharmLicenceDto);
                    return null;
                } else {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                    pharmLicence.setId(UUID.randomUUID());
                    pharmLicence.setBusinessPartner(businessPartner);
                    pharmLicence.setActivityType(pharmLicenceDto.getActivityType());
                    pharmLicence.setLicenceNumber(pharmLicenceDto.getNumber());
                    pharmLicence.setLicenceDate(LocalDate.parse(pharmLicenceDto.getDate(), dtf));
                    pharmLicence.setRznAddresses(rznAddresses);
                    pharmLicence.setTerminated(isTerminated);

                    return pharmLicence;
                }
            }
        }
    }
    
    public static List<RznAddress> getRznAddresses(PharmLicenceDto pharmLicenceDto, PharmLicence pharmLicence){
        List<RznAddress> rznAddresses = new ArrayList<>();
        for (RznAddressDto rznAddressDto : pharmLicenceDto.getAddressList()) {
            log.info("Обрабатываю адрес {} для лицензии {}", rznAddressDto, pharmLicenceDto.getNumber());

            RznAddress rznAddress = new RznAddress();

            Fias fias = getFiasAddress(rznAddressDto);

            if (fias != null) {
                rznAddress.setId(UUID.randomUUID());
                rznAddress.setPharmLicence(pharmLicence);
                rznAddress.setRznAddress(rznAddressDto.getAddress());
                rznAddress.setFias(fias);

                Set<WorkDescription> workDescriptions = getWorkDescriptions(rznAddressDto, rznAddress);
                if (workDescriptions != null) {
                    rznAddress.setWorkDescriptions(workDescriptions);
                    rznAddresses.add(rznAddress);
                }
            }
            else {
                log.info("Для адреса не указан фиас {}. Обработка видов деятельности не начата", rznAddressDto);
            }
        }
        return rznAddresses;
    }
    
    public static Set<WorkDescription> getWorkDescriptions (RznAddressDto rznAddressDto, RznAddress rznAddress){
        if (rznAddressDto.getWorks().isEmpty()
                || rznAddressDto.getWorks() == null) {
            log.info("Нет перечня видов деятельности для адреса {}", rznAddressDto);
            return null;
        }
        else {
            try {
                Set<WorkDescription> workDescriptions = new HashSet<>();
                for (WorkDescriptionDto work : rznAddressDto.getWorks()) {
                    if (!work.getWork().isEmpty())
                        workDescriptions.add(new WorkDescription(
                                UUID.randomUUID(),
                                rznAddress,
                                work.getWork()
                        ));
                    else {
                        log.info("Для адреса {} есть пустые виды деятельности", rznAddressDto);
                    }
                }
                return workDescriptions;
            }
            catch (Exception e){
                return null;
            }
        }
    }

    public static Fias getFiasAddress(RznAddressDto rznAddressDto){
        if (rznAddressDto.getFias().isEmpty())
            return null;
        else {
            String fiasAddr = rznAddressDto.getFias();
            if (fiasAddr.length() < 36)
                return null;

            if (fiasAddr.length() > 36)
                fiasAddr = fiasAddr.substring(0,36);

            try {
                UUID fiasUuid = UUID.fromString(fiasAddr);
                return new Fias(
                        fiasUuid,
                        UUID.fromString("00000000-0000-0000-0000-000000000000")

                );
            }
            catch (Exception e){
                log.error(e.getLocalizedMessage());
                return null;
            }
        }
    }
}
