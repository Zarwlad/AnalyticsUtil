package ru.zarwlad.mdlp.downloadMdlpPartners.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.Fias;
import ru.zarwlad.mdlp.downloadMdlpPartners.model.PharmLicence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

public class PharmLicenceJdbcDAO implements DAO<PharmLicence, UUID> {
    private static Logger log = LoggerFactory.getLogger(PharmLicenceJdbcDAO.class);
    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private static String url = properties.getProperty("connectionMdlpDb");
    private static String user = properties.getProperty("userMdlpDb");
    private static String pass = properties.getProperty("passwordMdlpDb");


    @Override
    public void create(PharmLicence pharmLicence) {
        try (Connection connection = DriverManager.getConnection(url, user, pass)){
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO licence (id, business_partner_id, licence_number, licence_date, is_terminated, activity_type) " +
                                "values " + "(?, ?, ?, ?, ?, ?)");
                preparedStatement.setObject(1, pharmLicence.getId());
                preparedStatement.setObject(2, pharmLicence.getBusinessPartner().getSystemSubjId());
                preparedStatement.setString(3, pharmLicence.getLicenceNumber());
                preparedStatement.setObject(4, pharmLicence.getLicenceDate());
                preparedStatement.setBoolean(5, pharmLicence.isTerminated());
                preparedStatement.setString(6, pharmLicence.getActivityType());
                preparedStatement.executeUpdate();
                preparedStatement.close();

        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
    }

    @Override
    public void delete(PharmLicence pharmLicence) {

    }

    @Override
    public void update(PharmLicence pharmLicence) {

    }

    @Override
    public PharmLicence read(UUID uuid) {
        return null;
    }

    public PharmLicence readByNumAndDate(PharmLicence pharmLicence){
        PharmLicence pharmLicenceDb = null;

        try (Connection connection = DriverManager.getConnection(url, user, pass)){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM licence WHERE licence_number = (?) AND  licence_date = (?)");
            preparedStatement.setString(1, pharmLicence.getLicenceNumber());
            preparedStatement.setObject(2, pharmLicence.getLicenceDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                pharmLicenceDb = new PharmLicence();
                pharmLicenceDb.setLicenceNumber(resultSet.getString("licence_number"));
                pharmLicenceDb.setLicenceDate(resultSet.getDate("licence_date").toLocalDate());
            }
            preparedStatement.close();
        }
        catch (SQLException e){
            log.error(e.getLocalizedMessage(), e.getSQLState());
        }
        return pharmLicenceDb;
    }
}
