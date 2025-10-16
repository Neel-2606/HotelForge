package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Load MySQL JDBC Driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found. Make sure mysql-connector-j is in the classpath.", e);
            }

            // Load properties file from src/main/java
            Properties props = new Properties();
            try {
                FileInputStream fis = new FileInputStream("src/main/java/db.properties");
                props.load(fis);
            } catch (IOException e) {
                throw new SQLException("Failed to read db.properties. Make sure it exists in src/main/java/", e);
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new SQLException("Missing required properties in db.properties. Need db.url, db.user, and db.password");
            }

            // Connect to MySQL
            connection = DriverManager.getConnection(url, user, password);
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            System.out.println("Connected to DB successfully!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            throw e;
        }
    }
}
