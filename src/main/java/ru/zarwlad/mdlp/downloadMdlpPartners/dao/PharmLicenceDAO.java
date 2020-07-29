package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;

import java.util.UUID;

public class PharmLicenceDAO implements DAO<PharmLicence, UUID>{
    private static SessionFactory sessionFactory;

    public PharmLicenceDAO(SessionFactory sessionFactory){
        PharmLicenceDAO.sessionFactory = sessionFactory;
    }

    @Override
    public void create(PharmLicence pharmLicence) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.saveOrUpdate(pharmLicence);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delete(PharmLicence pharmLicence) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(pharmLicence);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void update(PharmLicence pharmLicence) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(pharmLicence);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public PharmLicence read(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(PharmLicence.class, id);
        }
    }
}
