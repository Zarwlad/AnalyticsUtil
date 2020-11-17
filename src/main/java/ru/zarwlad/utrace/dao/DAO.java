package ru.zarwlad.utrace.dao;

public interface DAO<Entity,Id> {

    void create(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
    Entity readById(Id id);
}
