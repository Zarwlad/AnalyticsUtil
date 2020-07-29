package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.WorkDescription;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class WorkDescriptionJdbcDAO implements DAO<WorkDescription, UUID> {
    private static final Logger log = LoggerFactory.getLogger(WorkDescriptionJdbcDAO.class);
    private static final Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private static final String url = properties.getProperty("connectionMdlpDb");
    private static final String user = properties.getProperty("userMdlpDb");
    private static final String pass = properties.getProperty("passwordMdlpDb");


    @Override
    public void create(WorkDescription workDescription) {
        try (Connection connection = DriverManager.getConnection(url, user, pass)){
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO rzn_work (id, rzn_address_id, work_description) " +
                                "values " + "(?, ?, ?)");
                preparedStatement.setObject(1, workDescription.getId());
                preparedStatement.setObject(2, workDescription.getRznAddress().getId());
                preparedStatement.setString(3, workDescription.getWorkDescription());
                preparedStatement.executeUpdate();
                preparedStatement.close();
        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
    }

    @Override
    public void delete(WorkDescription workDescription) {

    }

    @Override
    public void update(WorkDescription workDescription) {

    }

    @Override
    public WorkDescription read(UUID uuid) {
        return null;
    }
}
