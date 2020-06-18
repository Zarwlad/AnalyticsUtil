package ru.zarwlad.sgtinsFromMdlpAnalitics.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sgtin;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sscc;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SsccDao implements DAO<Sscc> {

    @Override
    public Sscc create(SessionFactory sessionFactory, Sscc sscc) {
        try (Session session = sessionFactory.openSession()){
            Sscc ssccFromDb = readByUnique(sessionFactory, sscc);
            if (ssccFromDb == null){
                session.beginTransaction();
                session.save(sscc);
                session.getTransaction().commit();
                ssccFromDb = readByUnique(sessionFactory, sscc);
            }
            return ssccFromDb;
        }
    }

    @Override
    public void update(SessionFactory sessionFactory, Sscc sscc) {

    }

    @Override
    public Sscc readByUnique(SessionFactory sessionFactory, Sscc sscc) {
        List<Sscc> ssccList = null;

        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Sscc> ssccCriteriaQuery = criteriaBuilder.createQuery(Sscc.class);
            Root<Sscc> ssccRoot = ssccCriteriaQuery.from(Sscc.class);
            ssccCriteriaQuery.select(ssccRoot);
            ssccCriteriaQuery.where(
                    criteriaBuilder.equal(ssccRoot.get("sscc"), sscc.getSscc())
            );

            session.beginTransaction();
            ssccList = session.createQuery(ssccCriteriaQuery).getResultList();
        }
        if (ssccList.size() != 0)
            return ssccList.get(0);
        else
            return null;
    }

    @Override
    public void delete(SessionFactory sessionFactory, Sscc sscc) {

    }
}
