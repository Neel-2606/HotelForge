package com.hotel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple Database Connection class
 * Demonstrates: Basic JDBC connection
 */
public class SimpleConnection {
    
    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password"; // Change this to your MySQL password
    
    /**
     * Get database connection
     * Demonstrates: Exception handling with try-catch
     */
    public static Connection getConnection() {
        Connection connection = null;
        
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create connection
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            
            System.out.println("Database connected successfully!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test if database connection works
     */
    public static boolean testConnection() {
        Connection connection = getConnection();
        
        if (connection != null) {
            closeConnection(connection);
            return true;
        }
        
        return false;
    }
}
