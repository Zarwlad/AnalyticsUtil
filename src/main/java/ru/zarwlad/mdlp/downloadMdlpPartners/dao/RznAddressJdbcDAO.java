package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.Fias;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.RznAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

public class RznAddressJdbcDAO implements DAO<RznAddress, UUID> {
    private static final Logger log = LoggerFactory.getLogger(RznAddressJdbcDAO.class);
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
    public void create(RznAddress rznAddress) {
        try (Connection connection = DriverManager.getConnection(url, user, pass)){
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO rzn_address (id, licence_id, fias_id, rzn_address) " +
                                "values " + "(?, ?, ?, ?)");
                preparedStatement.setObject(1, rznAddress.getId());
                preparedStatement.setObject(2, rznAddress.getPharmLicence().getId());
                preparedStatement.setObject(3, rznAddress.getFias().getHouseGuid());
                preparedStatement.setString(4, rznAddress.getRznAddress());
                preparedStatement.executeUpdate();
                preparedStatement.close();

        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
    }

    @Override
    public void delete(RznAddress rznAddress) {

    }

    @Override
    public void update(RznAddress rznAddress) {

    }

    @Override
    public RznAddress read(UUID uuid) {
        return null;
    }

    public RznAddress readFiasAndLicence(RznAddress rznAddress){
        RznAddress rznAddressDb = null;

        try (Connection connection = DriverManager.getConnection(url, user, pass)){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM rzn_address WHERE fias_id = (?) AND  licence_id = (?)");
            preparedStatement.setObject(1, rznAddress.getFias().getHouseGuid());
            preparedStatement.setObject(2, rznAddress.getPharmLicence().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                rznAddressDb = new RznAddress();
                rznAddressDb.setFias(new Fias());
                rznAddressDb.setPharmLicence(new PharmLicence());
            }
            preparedStatement.close();
        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
        return rznAddress;
    }
}
