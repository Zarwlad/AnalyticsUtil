package ru.zarwlad.utrace.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDb {
    private static final Logger log = LoggerFactory.getLogger(ConnectionDb.class);
    private static final Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src\\main\\resources\\application.properties"));
        } catch (IOException e) {
            log.error("Ошибка чтения application.properties {}", e.toString());
        }
    }
    private Connection connection;

    public ConnectionDb(){
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("connectionDb"),
                    properties.getProperty("userDb"),
                    properties.getProperty("passwordDb"));
        } catch (SQLException e) {
            log.error("Ошибка подключения к бд {}", e.toString());
        }
    }

    public Connection getConnection() {
        return connection;
    }


}
