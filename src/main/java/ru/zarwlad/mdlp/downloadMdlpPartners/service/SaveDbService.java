package ru.zarwlad.mdlp.downloadMdlpPartners.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.zarwlad.mdlp.downloadMdlpPartners.dao.BusinessPartnerDAO;
import ru.zarwlad.mdlp.downloadMdlpPartners.dao.FiasDAO;
import ru.zarwlad.mdlp.downloadMdlpPartners.dao.LocationDAO;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.Location;

import java.util.List;

public class SaveDbService {
    public static void saveBusinessPartnersToDb(List<BusinessPartner> businessPartnerList){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        BusinessPartnerDAO businessPartnerDAO = new BusinessPartnerDAO(sessionFactory);
        LocationDAO locationDAO = new LocationDAO(sessionFactory);
        FiasDAO fiasDAO = new FiasDAO(sessionFactory);

        for (BusinessPartner businessPartner : businessPartnerList) {
            try {
                businessPartnerDAO.create(businessPartner);
            }
            catch (Exception e){};


            for (Location location : businessPartner.getLocations()) {
                try {
                    fiasDAO.create(location.getFias());
                }
                catch (Exception e){};
                try {
                    locationDAO.create(location);
                }
                catch (Exception e){}
            }
        }
    }
}
