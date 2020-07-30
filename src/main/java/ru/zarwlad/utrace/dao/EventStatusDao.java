package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.EventStatus;

import java.util.UUID;

public class EventStatusDao extends AbstractDAO implements DAO<EventStatus, UUID>{
    private Logger log = LoggerFactory.getLogger(EventStatusDao.class);

    public EventStatusDao(SessionFactory sessionFactory){
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void create(EventStatus eventStatus) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(eventStatus);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(EventStatus eventStatus) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(eventStatus);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(EventStatus eventStatus) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(eventStatus);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public EventStatus readById(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(EventStatus.class, id);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
