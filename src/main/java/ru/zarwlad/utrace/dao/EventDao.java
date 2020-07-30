package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;

import java.sql.Connection;
import java.util.UUID;

public class EventDao extends AbstractDAO implements DAO<Event, UUID>{
    private Logger log = LoggerFactory.getLogger(EventDao.class);

    public EventDao(SessionFactory sessionFactory){
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Event event) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Event event) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(event);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Event event) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(event);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public Event readById(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Event.class, id);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
