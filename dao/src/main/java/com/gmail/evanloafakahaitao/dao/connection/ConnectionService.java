package com.gmail.evanloafakahaitao.dao.connection;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private Connection connection = null;

    private static volatile ConnectionService instance = null;

    private ConnectionService() {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        try {
            Class.forName(configurationManager.getProperty(configurationManager.DATABASE_PROPERTIES.DATABASE_DRIVER_NAME));
            System.out.println("Sql JDBC driver was succesfully loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Sql JDBC driver loading failed");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(
                    configurationManager.getProperty(configurationManager.DATABASE_PROPERTIES.DATABASE_URL),
                    configurationManager.getProperty(configurationManager.DATABASE_PROPERTIES.DATABASE_USERNAME),
                    configurationManager.getProperty(configurationManager.DATABASE_PROPERTIES.DATABASE_PWD)
            );
            if (connection == null) {
                throw new RuntimeException("Cant gain access to DB");
            }
            System.out.println("Connection to DB established");
        } catch (SQLException e) {
            System.out.println("Couldnt establish connection to DB");
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionService getInstance() {
        if (instance == null) {
            synchronized (ConnectionService.class) {
                if (instance == null) {
                    instance = new ConnectionService();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println("Error closing connection");
            e.printStackTrace();
        }
    }
}
