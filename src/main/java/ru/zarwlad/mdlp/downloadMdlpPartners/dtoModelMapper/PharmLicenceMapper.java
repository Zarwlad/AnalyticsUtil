package ru.zarwlad.mdlp.downloadMdlpPartners.dtoModelMapper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.PharmLicenceDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto.RznAddressDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.RznAddress;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.WorkDescription;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

public class PharmLicenceMapper {
    public static PharmLicence fromDtoToEntity(PharmLicenceDto pharmLicenceDto){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        BusinessPartner businessPartner = null;

        try (Session session= sessionFactory.openSession()){
            CriteriaBuilder builder = sessionFactory.openSession().getCriteriaBuilder();
            CriteriaQuery<BusinessPartner> cq = builder.createQuery(BusinessPartner.class);
            Root<BusinessPartner> root = cq.from(BusinessPartner.class);
            cq.select(root).where(builder.equal(root.get("inn"),pharmLicenceDto.getInn()));

            Query<BusinessPartner> query = session.createQuery(cq);

            List<BusinessPartner> results = query.getResultList();

            for (BusinessPartner result : results) {
                businessPartner = result;
            }
        }

        if (businessPartner == null)
            return null;
        else {
            
            
            return new PharmLicence();
        }
    }
    
    public static List<RznAddress> rznAddresses(Session session, PharmLicenceDto pharmLicenceDto){

        return null;
    }
    
    public static List<WorkDescription> workDescriptions (RznAddressDto rznAddressDto){
        return null;
    }
}
