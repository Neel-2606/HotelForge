package com.hotel.main;

import com.hotel.database.SimpleConnection;
import com.hotel.ui.SimpleBookingPanel;
import javax.swing.*;
import java.sql.Connection;

/**
 * Simple Hotel Management Application
 * Main class to run the booking management system
 * 
 * Demonstrates:
 * 1. Main method
 * 2. Object creation
 * 3. Basic program flow
 */
public class SimpleHotelApp {
    
    /**
     * Main method - entry point of the program
     */
    public static void main(String[] args) {
        
        System.out.println("=== Hotel Management System ===");
        System.out.println("Starting application...");
        
        // Test database connection first
        System.out.println("Testing database connection...");
        
        if (SimpleConnection.testConnection()) {
            System.out.println("Database connection successful!");
            
            // Create the main application window
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    createAndShowGUI();
                }
            });
            
        } else {
            System.out.println("Database connection failed!");
            System.out.println("Please check:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database 'hotel_management' exists");
            System.out.println("3. Username and password are correct");
            
            // Show error dialog
            JOptionPane.showMessageDialog(null, 
                "Database connection failed!\nPlease check MySQL server and database settings.", 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Create and show the main GUI
     * Demonstrates: Object creation, Method calling
     */
    private static void createAndShowGUI() {
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            
            // Get database connection
            Connection connection = SimpleConnection.getConnection();
            
            if (connection != null) {
                // Create main window
                SimpleBookingPanel mainWindow = new SimpleBookingPanel(connection);
                
                // Show the window
                mainWindow.setVisible(true);
                
                System.out.println("Application started successfully!");
                
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Could not connect to database!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.out.println("Error starting application: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error starting application: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
