package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.WorkDescription;

import java.util.UUID;

public class WorkDescriptionDAO implements DAO<WorkDescription, UUID>{
    private static SessionFactory sessionFactory;

    public WorkDescriptionDAO(SessionFactory sessionFactory){
        WorkDescriptionDAO.sessionFactory = sessionFactory;
    }

    @Override
    public void create(WorkDescription workDescription) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.saveOrUpdate(workDescription);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delete(WorkDescription workDescription) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(workDescription);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void update(WorkDescription workDescription) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(workDescription);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public WorkDescription read(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(WorkDescription.class, id);
        }
    }
}
