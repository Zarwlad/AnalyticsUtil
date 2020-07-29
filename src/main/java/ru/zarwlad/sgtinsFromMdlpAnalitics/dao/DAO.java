package ru.zarwlad.sgtinsFromMdlpAnalitics.dao;

import org.hibernate.SessionFactory;

public interface DAO<T> {
    T create(SessionFactory sessionFactory, T t);
    void update(SessionFactory sessionFactory, T t);
    T readByUnique(SessionFactory sessionFactory, T t);
    void delete(SessionFactory sessionFactory, T t);
}
