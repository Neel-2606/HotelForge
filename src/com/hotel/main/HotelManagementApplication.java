package com.hotel.main;

import com.hotel.database.UnifiedDatabaseConnection;
import com.hotel.ui.UnifiedLoginForm;
import javax.swing.*;

/**
 * Main Application Entry Point for Hotel Management System
 * This class initializes the application and starts the login process
 */
public class HotelManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Hotel Management System ===");
        System.out.println("Starting application...");
        
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Test database connection
        System.out.println("Testing database connection...");
        
        if (UnifiedDatabaseConnection.testConnection()) {
            System.out.println("Database connection successful!");
            
            // Start the application on EDT
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new UnifiedLoginForm();
                        System.out.println("Login form displayed successfully!");
                    } catch (Exception e) {
                        System.err.println("Error starting login form: " + e.getMessage());
                        e.printStackTrace();
                        
                        JOptionPane.showMessageDialog(null, 
                            "Error starting application: " + e.getMessage(), 
                            "Application Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
        } else {
            System.err.println("Database connection failed!");
            System.err.println("Please check:");
            System.err.println("1. MySQL server is running");
            System.err.println("2. Database 'hotel_management' exists");
            System.err.println("3. Username and password are correct in UnifiedDatabaseConnection.java");
            
            // Show error dialog
            JOptionPane.showMessageDialog(null, 
                "Database connection failed!\n\n" +
                "Please ensure:\n" +
                "1. MySQL server is running\n" +
                "2. Database 'hotel_management' exists\n" +
                "3. Username/password are correct\n\n" +
                "Check console for more details.", 
                "Database Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
        }
    }
}
