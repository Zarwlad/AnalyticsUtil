package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

public class EventStatDao extends AbstractDAO implements DAO<EventStat, UUID>{
    private Logger log = LoggerFactory.getLogger(EventStatDao.class);

    public EventStatDao(SessionFactory sessionFactory){
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void create(EventStat eventStat) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(eventStat);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(EventStat eventStat) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(eventStat);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(EventStat eventStat) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(eventStat);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public EventStat readById(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(EventStat.class, id);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public List<EventStat> readAll(){
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EventStat> query = builder.createQuery(EventStat.class);
            query.from(EventStat.class);
            return session.createQuery(query).getResultList();
        }
    }

    public List<EventStat> readByEventId(UUID id){
        try (Session session = sessionFactory.openSession()){
            String hql = "SELECT e FROM EventStat e WHERE e.event.id = :id";
            return session.createQuery(hql).setParameter("id", id).getResultList();
        }
    }

    public List<Event> readAllCalculatedEvents(){
        try (Session session = sessionFactory.openSession()){
            String hql = "SELECT DISTINCT e.event FROM EventStat e";
            return session.createQuery(hql).getResultList();
        }
    }
}
