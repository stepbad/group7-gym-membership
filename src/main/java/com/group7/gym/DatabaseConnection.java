package com.group7.gym;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles loading database credentials from a properties file and connecting to the database.
 */
public class DatabaseConnection {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private static final String PROPERTIES_FILE = "db.properties";

    /**
     * Establishes and returns a connection to the PostgreSQL database.
     *
     * @return Active database connection, or null if connection fails
     */
    public static Connection getConnection() {
        Connection connection = null;

        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                logger.severe("Unable to find " + PROPERTIES_FILE);
                return null;
            }

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Database connection established.");

        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "PostgreSQL JDBC driver not found.", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL exception during DB connection.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while loading DB config.", e);
        }

        return connection;
    }
}
