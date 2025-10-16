package com.hotel.ui;

import com.hotel.models.User;
import com.hotel.services.AuthenticationService;
import main.ui.RoomManagementPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Application Window for Hotel Management System
 * Integrates all modules: Authentication, Room Management, Booking Management
 */
public class HotelManagementMainApp extends JFrame {
    
    private User currentUser;
    private JTabbedPane tabbedPane;
    private JLabel userInfoLabel;
    private JButton logoutButton;
    
    public HotelManagementMainApp(User user) {
        this.currentUser = user;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadModules();
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("Hotel Management System - " + currentUser.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        tabbedPane = new JTabbedPane();
        userInfoLabel = new JLabel();
        logoutButton = new JButton("Logout");
        
        // Set user info
        updateUserInfo();
        
        // Style logout button
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 58, 64));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Hotel Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // User info panel
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(new Color(52, 58, 64));
        userInfoLabel.setForeground(Color.WHITE);
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userPanel.add(userInfoLabel);
        userPanel.add(Box.createHorizontalStrut(20));
        userPanel.add(logoutButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        // Main content
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        JLabel footerLabel = new JLabel("© 2024 Hotel Management System - Integrated Solution");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(108, 117, 125));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogout();
            }
        });
        
        // Window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                performLogout();
                System.exit(0);
            }
        });
    }
    
    private void loadModules() {
        try {
            // Dashboard (always available)
            JPanel dashboardPanel = createDashboardPanel();
            tabbedPane.addTab("Dashboard", new ImageIcon(), dashboardPanel, "System Overview");
            
            // Room Management (available to all users)
            try {
                RoomManagementPanel roomPanel = new RoomManagementPanel();
                // Remove the frame behavior and use as panel
                roomPanel.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                tabbedPane.addTab("Room Management", new ImageIcon(), roomPanel.getContentPane(), "Manage Hotel Rooms");
            } catch (Exception e) {
                System.err.println("Error loading Room Management: " + e.getMessage());
                JPanel errorPanel = createErrorPanel("Room Management", e.getMessage());
                tabbedPane.addTab("Room Management", new ImageIcon(), errorPanel, "Room Management (Error)");
            }
            
            // Booking Management (available to all users, but different views)
            try {
                if (currentUser.isAdmin()) {
                    BookingManagementPanel bookingPanel = new BookingManagementPanel();
                    tabbedPane.addTab("Booking Management", new ImageIcon(), bookingPanel, "Manage All Bookings");
                } else {
                    SimpleBookingPanel customerBookingPanel = new SimpleBookingPanel();
                    tabbedPane.addTab("My Bookings", new ImageIcon(), customerBookingPanel, "My Booking History");
                }
            } catch (Exception e) {
                System.err.println("Error loading Booking Management: " + e.getMessage());
                JPanel errorPanel = createErrorPanel("Booking Management", e.getMessage());
                tabbedPane.addTab("Booking Management", new ImageIcon(), errorPanel, "Booking Management (Error)");
            }
            
            // Admin-only modules
            if (currentUser.isAdmin()) {
                // User Management
                JPanel userManagementPanel = createUserManagementPanel();
                tabbedPane.addTab("User Management", new ImageIcon(), userManagementPanel, "Manage System Users");
                
                // Reports
                JPanel reportsPanel = createReportsPanel();
                tabbedPane.addTab("Reports", new ImageIcon(), reportsPanel, "System Reports and Analytics");
            }
            
        } catch (Exception e) {
            System.err.println("Error loading modules: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error loading application modules: " + e.getMessage(), 
                "Module Loading Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Welcome message
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(240, 248, 255));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel welcomeLabel = new JLabel("<html><h1>Welcome to Hotel Management System</h1>" +
                "<p>Hello, " + currentUser.getFullName() + "!</p>" +
                "<p>Role: " + currentUser.getRole() + "</p></html>");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomePanel.add(welcomeLabel);
        
        // Quick stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsPanel.setBackground(Color.WHITE);
        
        // Add stat cards
        statsPanel.add(createStatCard("Total Rooms", "10", new Color(0, 123, 255)));
        statsPanel.add(createStatCard("Available Rooms", "7", new Color(40, 167, 69)));
        statsPanel.add(createStatCard("Active Bookings", "3", new Color(255, 193, 7)));
        statsPanel.add(createStatCard("Today's Check-ins", "2", new Color(220, 53, 69)));
        
        panel.add(welcomePanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel("<html><h2>User Management</h2><p>Manage system users and permissions</p></html>");
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);
        
        // Add user management functionality here
        JTextArea textArea = new JTextArea("User Management functionality will be implemented here.\n\n" +
                "Features:\n" +
                "- View all users\n" +
                "- Add new users\n" +
                "- Edit user details\n" +
                "- Manage user roles\n" +
                "- Deactivate users");
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel("<html><h2>Reports & Analytics</h2><p>System reports and business analytics</p></html>");
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);
        
        // Add reports functionality here
        JTextArea textArea = new JTextArea("Reports functionality will be implemented here.\n\n" +
                "Available Reports:\n" +
                "- Occupancy Reports\n" +
                "- Revenue Reports\n" +
                "- Customer Analytics\n" +
                "- Room Utilization\n" +
                "- Booking Trends");
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createErrorPanel(String moduleName, String errorMessage) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel errorLabel = new JLabel("<html><h2>Error Loading " + moduleName + "</h2>" +
                "<p style='color: red;'>Error: " + errorMessage + "</p>" +
                "<p>Please check the console for more details.</p></html>");
        errorLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(errorLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void updateUserInfo() {
        userInfoLabel.setText("Welcome, " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
    }
    
    private void performLogout() {
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            AuthenticationService.logout();
            dispose();
            
            // Return to login screen
            SwingUtilities.invokeLater(() -> {
                new UnifiedLoginForm();
            });
        }
    }
}
