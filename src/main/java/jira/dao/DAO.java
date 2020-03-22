package jira.dao;

import java.util.UUID;

public interface DAO {
    DAO findById(UUID id);

    void create (DAO obj);

    void update (DAO obj);

    void read ();

    void delete (DAO obj);

}
