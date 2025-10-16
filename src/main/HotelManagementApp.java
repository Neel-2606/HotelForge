package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import com.hotel.ui.RoomManagementPanel;
import com.hotel.ui.BookingManagementPanel;
import ui.LoginForm;
import utils.SessionManager;

/**
 * Main application class that integrates all modules of the Hotel Management System
 */
public class HotelManagementApp {
    private static JFrame mainFrame;
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Start with login form
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    super.actionPerformed(e);
                    if (SessionManager.getCurrentUser() != null) {
                        // Launch main application after successful login
                        launchMainApplication();
                    }
                }
            };
            loginForm.setVisible(true);
        });
    }
    
    /**
     * Launch the main application with tabbed interface
     */
    private static void launchMainApplication() {
        mainFrame = new JFrame("Hotel Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1100, 700);
        mainFrame.setLocationRelativeTo(null);
        
        // Create tabbed pane for different modules
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Add Room Management Panel
        RoomManagementPanel roomPanel = new RoomManagementPanel();
        tabbedPane.addTab("Room Management", roomPanel.getContentPane());
        
        // Add Booking Management Panel
        try {
            BookingManagementPanel bookingPanel = new BookingManagementPanel();
            tabbedPane.addTab("Booking Management", bookingPanel);
        } catch (Exception e) {
            JPanel errorPanel = new JPanel();
            errorPanel.add(new JLabel("Error loading Booking Management: " + e.getMessage()));
            tabbedPane.addTab("Booking Management", errorPanel);
        }
        
        // Add header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        
        JLabel titleLabel = new JLabel("Hotel Management System - Admin Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel userLabel = new JLabel("  Logged in as: " + SessionManager.getCurrentUser());
        userLabel.setForeground(Color.WHITE);
        headerPanel.add(userLabel, BorderLayout.EAST);
        
        // Add components to main frame
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(tabbedPane, BorderLayout.CENTER);
        
        // Show the frame
        mainFrame.setVisible(true);
    }
}