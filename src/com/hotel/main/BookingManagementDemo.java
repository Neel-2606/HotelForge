package com.hotel.main;

import com.hotel.database.DatabaseConnection;
import com.hotel.ui.BookingManagementPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Demo application for Booking Management Module
 * Member 4 - Booking Management (Admin Side)
 */
public class BookingManagementDemo extends JFrame {
    
    public BookingManagementDemo() {
        setTitle("Hotel Management System - Booking Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        try {
            // Test database connection
            Connection connection = DatabaseConnection.getConnection();
            
            if (connection != null) {
                // Create and add booking management panel
                BookingManagementPanel bookingPanel = new BookingManagementPanel(connection);
                add(bookingPanel, BorderLayout.CENTER);
                
                // Add header
                JPanel headerPanel = new JPanel();
                headerPanel.setBackground(new Color(70, 130, 180));
                JLabel headerLabel = new JLabel("Hotel Management System - Admin Dashboard");
                headerLabel.setForeground(Color.WHITE);
                headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
                headerPanel.add(headerLabel);
                add(headerPanel, BorderLayout.NORTH);
                
                // Add footer
                JPanel footerPanel = new JPanel();
                footerPanel.add(new JLabel("Member 4 - Booking Management Module"));
                add(footerPanel, BorderLayout.SOUTH);
                
            } else {
                showDatabaseError();
            }
            
        } catch (SQLException e) {
            showDatabaseError();
            e.printStackTrace();
        }
        
        // Set window properties
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void showDatabaseError() {
        JPanel errorPanel = new JPanel(new BorderLayout());
        JLabel errorLabel = new JLabel(
            "<html><center>" +
            "<h2>Database Connection Error</h2>" +
            "<p>Please ensure MySQL is running and the database is set up correctly.</p>" +
            "<p>Check the connection settings in DatabaseConnection.java</p>" +
            "</center></html>"
        );
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorPanel.add(errorLabel, BorderLayout.CENTER);
        add(errorPanel, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show the application
        SwingUtilities.invokeLater(() -> new BookingManagementDemo());
    }
}
