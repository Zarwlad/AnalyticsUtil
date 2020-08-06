package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Event;
import ru.zarwlad.utrace.model.EventStatus;
import ru.zarwlad.utrace.model.Message;

import java.util.List;
import java.util.UUID;

public class MessageDao extends AbstractDAO implements DAO<Message, UUID>{
    private Logger log = LoggerFactory.getLogger(MessageDao.class);

    public MessageDao(SessionFactory sessionFactory){
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Message message) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Message message) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(message);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Message message) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(message);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public Message readById(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Message.class, id);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public List<Message> readByEventId(UUID id){
        try (Session session = sessionFactory.openSession()){
            String hql = "SELECT e FROM Message e WHERE e.event.id = :id";
            return session.createQuery(hql).setParameter("id", id).getResultList();
        }
    }
}
