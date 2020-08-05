package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatistic;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

public class EventStatDao extends AbstractDAO implements DAO<EventStatistic, UUID>{
    private Logger log = LoggerFactory.getLogger(EventStatDao.class);

    public EventStatDao(SessionFactory sessionFactory){
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void create(EventStatistic eventStatistic) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(eventStatistic);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(EventStatistic eventStatistic) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(eventStatistic);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(EventStatistic eventStatistic) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(eventStatistic);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public EventStatistic readById(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(EventStatistic.class, id);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public List<EventStatistic> readAll(){
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EventStatistic> query = builder.createQuery(EventStatistic.class);
            query.from(EventStatistic.class);
            return session.createQuery(query).getResultList();
        }
    }
}
