package ru.zarwlad.utrace.dao;

public interface DAO<Entity,UUID> {
    void create();
    void update();
    void delete();
    Entity readById();
}
