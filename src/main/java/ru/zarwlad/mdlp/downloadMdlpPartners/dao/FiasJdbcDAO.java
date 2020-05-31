package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.Fias;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.RznAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class FiasJdbcDAO implements DAO<Fias, UUID> {
    private static final Logger log = LoggerFactory.getLogger(FiasJdbcDAO.class);
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
    public void create(Fias fias) {
        try (Connection connection = DriverManager.getConnection(url, user, pass)){
            Fias fiasDb = read(fias.getHouseGuid());
            if (fiasDb == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO fias (house_guid, ao_guid) " +
                                "values " + "(?, ?)");
                preparedStatement.setObject(1, fias.getHouseGuid());
                preparedStatement.setObject(2, fias.getAoGuid());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
    }

    @Override
    public void delete(Fias fias) {

    }

    @Override
    public void update(Fias fias) {

    }

    @Override
    public Fias read(UUID uuid) {
        Fias fias = null;

        try (Connection connection = DriverManager.getConnection(url, user, pass)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM fias WHERE house_guid = (?)");
            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                fias = new Fias(
                        (UUID) resultSet.getObject("house_guid"),
                        (UUID) resultSet.getObject("ao_guid")
                );
            }
        preparedStatement.close();
        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
        return fias;
    }
}
