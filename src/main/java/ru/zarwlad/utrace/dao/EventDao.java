package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.util.List;
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
                    "WHERE e.type != 'BASE' AND e.status != 'CANCELLED'" +
                    "AND e.id not in (select es.event.id from EventStat es)";
            Query query = session.createQuery(hql);
            query.setMaxResults(500);
            return query.getResultList();
        }
    }
}
