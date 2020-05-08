package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.Fias;

import java.util.UUID;

public class FiasDAO implements DAO<Fias, UUID>{
    private SessionFactory sessionFactory;

    public FiasDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Fias fias) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(fias);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Fias fias) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(fias);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Fias fias) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(fias);
            session.getTransaction().commit();
        }
    }

    @Override
    public Fias read(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Fias.class, id);
        }
    }
}
