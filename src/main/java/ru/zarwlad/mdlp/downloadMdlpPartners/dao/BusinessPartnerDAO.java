package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;

import java.util.UUID;

public class BusinessPartnerDAO implements DAO<BusinessPartner, UUID>{
    private SessionFactory sessionFactory;

    public BusinessPartnerDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(BusinessPartner businessPartner) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(businessPartner);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(BusinessPartner businessPartner) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(businessPartner);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(BusinessPartner businessPartner) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(businessPartner);
            session.getTransaction().commit();
        }
    }

    @Override
    public BusinessPartner read(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(BusinessPartner.class, id);
        }
    }
}
