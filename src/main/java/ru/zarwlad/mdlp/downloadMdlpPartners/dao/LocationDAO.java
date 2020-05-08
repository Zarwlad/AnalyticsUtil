package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.BusinessPartner;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.Location;

import java.util.UUID;

public class LocationDAO implements DAO<Location, String>{
    private SessionFactory sessionFactory;

    public LocationDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Location location) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Location location) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(location);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Location location) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(location);
            session.getTransaction().commit();
        }
    }

    @Override
    public Location read(String id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Location.class, id);
        }
    }
}
