package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.RznAddress;

import java.util.UUID;

public class RznAddressDAO implements DAO<RznAddress, UUID>{
    private static SessionFactory sessionFactory;

    public RznAddressDAO(SessionFactory sessionFactory){
        RznAddressDAO.sessionFactory = sessionFactory;
    }

    @Override
    public void create(RznAddress rznAddress) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.saveOrUpdate(rznAddress);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delete(RznAddress rznAddress) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(rznAddress);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void update(RznAddress rznAddress) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(rznAddress);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public RznAddress read(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(RznAddress.class, id);
        }
    }
}
