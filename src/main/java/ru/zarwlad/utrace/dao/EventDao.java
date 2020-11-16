package ru.zarwlad.utrace.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.util.client.PropertiesConfig;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Properties;
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

    public List<Event> readAll(){
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Event> query = builder.createQuery(Event.class);
            query.from(Event.class);
            return session.createQuery(query).getResultList();
        }
    }

    public List<Event> read500NotCalculatedEvents(){
        try (Session session = sessionFactory.openSession()){
            String hql = "SELECT e FROM Event e " +
                    "LEFT JOIN EventStat es ON e.id = es.event.id " +
                    "JOIN EventStatus est ON e.id = est.event.id " +
                    "WHERE e.type != 'BASE' " +
                    "AND e.status NOT IN ('CANCELLED', 'DRAFT', 'CREATED', 'FILLED')" +
                    "AND es.event IS NULL " +
                    "AND est.status = 'FILLED'";

            Query query = session.createQuery(hql);
            query.setMaxResults(500);
            return query.getResultList();
        }
    }

    public List<Event> readAllIdsFromEventsByClient(){
        try (Session session = sessionFactory.openSession()){
            String client = PropertiesConfig.properties.getProperty("client");

            String hql = "SELECT e FROM Event e " +
                    "WHERE e.client = :client ";

            Query query = session.createQuery(hql);
            query.setParameter("client", client);
            return query.getResultList();
        }
    }
}
