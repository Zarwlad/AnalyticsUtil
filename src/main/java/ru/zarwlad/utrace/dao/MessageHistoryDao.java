package ru.zarwlad.utrace.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.utrace.model.Message;
import ru.zarwlad.utrace.model.MessageHistory;

import java.util.List;
import java.util.UUID;

public class MessageHistoryDao extends AbstractDAO implements DAO<MessageHistory, UUID>{
    private Logger log = LoggerFactory.getLogger(MessageHistoryDao.class);

    public MessageHistoryDao(SessionFactory sessionFactory){
        super.sessionFactory = sessionFactory;
    }

    @Override
    public void create(MessageHistory messageHistory) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(messageHistory);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(MessageHistory messageHistory) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(messageHistory);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(MessageHistory messageHistory) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(messageHistory);
            session.getTransaction().commit();
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public MessageHistory readById(UUID id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(MessageHistory.class, id);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public List<MessageHistory> readByMsgId(UUID id){
        try (Session session = sessionFactory.openSession()){
            String hql = "SELECT e FROM MessageHistory e WHERE e.message.id = :id";
            return session.createQuery(hql).setParameter("id", id).getResultList();
        }
    }
}
