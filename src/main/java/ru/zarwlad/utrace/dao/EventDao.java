package ru.zarwlad.utrace.dao;

import ru.zarwlad.utrace.model.Event;

import java.sql.Connection;
import java.util.UUID;

public class EventDao implements DAO<Event, UUID> {
    private final Connection connection;

    EventDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public Event readById() {
        return null;
    }
}
