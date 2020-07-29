package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

public interface DAO<Entity, Id> {
    void create (Entity entity);
    void delete (Entity entity);
    void update (Entity entity);
    Entity read (Id id);
}
