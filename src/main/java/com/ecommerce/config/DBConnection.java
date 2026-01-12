package com.ecommerce.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class to manage database connections.
 * Reads configuration from db.properties file.
 */
public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private Properties properties;

    /**
     * Private constructor to prevent instantiation.
     * Loads database properties upon initialization.
     */
    private DBConnection() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Returns the singleton instance of DBConnection.
     * @return DBConnection instance
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Establishes and returns a connection to the database.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(properties.getProperty("db.driver"));
                connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
                );
            } catch (ClassNotFoundException e) {
                throw new SQLException("Database driver not found", e);
            }
        }
        return connection;
    }

    /**
     * Loads properties from the classpath resource.
     */
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find db.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }
    }
}