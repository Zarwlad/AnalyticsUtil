package ru.zarwlad.sgtinsFromMdlpAnalitics.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sgtin;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SgtinDao implements DAO<Sgtin> {
    @Override
    public Sgtin create(SessionFactory sessionFactory, Sgtin sgtin) {
        try (Session session = sessionFactory.openSession()){
            Sgtin sgtinFromDb = readByUnique(sessionFactory, sgtin);
            if (sgtinFromDb == null){
                session.beginTransaction();
                session.save(sgtin);
                session.getTransaction().commit();
                sgtinFromDb = readByUnique(sessionFactory, sgtin);
            }
            return sgtinFromDb;
        }
    }

    @Override
    public void update(SessionFactory sessionFactory, Sgtin sgtin) {

    }

    @Override
    public Sgtin readByUnique(SessionFactory sessionFactory, Sgtin sgtin) {
        List<Sgtin> sgtinList = null;

        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Sgtin> sgtinCriteriaQuery = criteriaBuilder.createQuery(Sgtin.class);
            Root<Sgtin> sgtinRoot = sgtinCriteriaQuery.from(Sgtin.class);
            sgtinCriteriaQuery.select(sgtinRoot);
            sgtinCriteriaQuery.where(
                    criteriaBuilder.equal(sgtinRoot.get("sgtin"), sgtin.getSgtin())
            );

            session.beginTransaction();
            sgtinList = session.createQuery(sgtinCriteriaQuery).getResultList();
        }
       if (sgtinList.size() != 0)
           return sgtinList.get(0);
       else
           return null;
    }

    @Override
    public void delete(SessionFactory sessionFactory, Sgtin sgtin) {
    }
}
