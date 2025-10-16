package com.hotel.ui;

import com.hotel.models.Booking;
import com.hotel.managers.SimpleBookingManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Simple Booking Panel - demonstrates basic Swing components
 * 1. JFrame, JPanel, JButton, JTable
 * 2. ActionListener for button clicks
 * 3. Basic event handling
 */
public class SimpleBookingPanel extends JPanel {
    
    // Swing components
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton cancelButton;
    private JTextField searchField;
    private JButton searchButton;
    
    // Business logic
    private SimpleBookingManager bookingManager;
    
    // Table column names
    private String[] columnNames = {
        "Booking ID", "Customer Name", "Room ID", "Check-In", 
        "Check-Out", "Status", "Amount", "Phone"
    };
    
    // Constructor
    public SimpleBookingPanel() {
        try {
            this.bookingManager = new SimpleBookingManager(com.hotel.database.UnifiedDatabaseConnection.getConnection());
        } catch (Exception e) {
            System.err.println("Error initializing booking manager: " + e.getMessage());
        }
        
        createComponents();
        setupLayout();
        addEventListeners();
        loadBookings();
    }
    
    
    /**
     * Create all Swing components
     */
    private void createComponents() {
        // Create table
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Create buttons
        refreshButton = new JButton("Refresh");
        checkInButton = new JButton("Check In");
        checkOutButton = new JButton("Check Out");
        cancelButton = new JButton("Cancel Booking");
        searchButton = new JButton("Search");
        
        // Create search field
        searchField = new JTextField(20);
    }
    
    /**
     * Setup layout using basic layout managers
     */
    private void setupLayout() {
        // Main panel
        setLayout(new BorderLayout());
        
        // Top panel for search
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Search Customer:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(refreshButton);
        
        // Center panel for table
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        
        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(checkInButton);
        bottomPanel.add(checkOutButton);
        bottomPanel.add(cancelButton);
        
        // Add panels to main frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Add event listeners to buttons
     * Demonstrates: ActionListener interface, Anonymous inner classes
     */
    private void addEventListeners() {
        
        // Refresh button listener
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookings();
            }
        });
        
        // Check-in button listener
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCheckIn();
            }
        });
        
        // Check-out button listener
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCheckOut();
            }
        });
        
        // Cancel button listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCancel();
            }
        });
        
        // Search button listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }
    
    /**
     * Load all bookings into table
     * Demonstrates: ArrayList usage, Table model operations
     */
    private void loadBookings() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Get bookings from manager
        ArrayList<Booking> bookings = bookingManager.getAllBookings();
        
        // Add each booking to table
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            
            Object[] row = {
                booking.getBookingId(),
                booking.getCustomerName(),
                booking.getRoomId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getStatus(),
                "₹" + booking.getTotalAmount(),
                booking.getCustomerPhone()
            };
            
            tableModel.addRow(row);
        }
        
        // Show message
        JOptionPane.showMessageDialog(this, 
            "Loaded " + bookings.size() + " bookings", 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Perform check-in operation
     * Demonstrates: Table selection, Conditional statements
     */
    private void performCheckIn() {
        int selectedRow = bookingTable.getSelectedRow();
        
        // Check if a row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a booking to check in", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get booking ID from selected row
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        // Check if booking can be checked in
        if (!status.equals("CONFIRMED")) {
            JOptionPane.showMessageDialog(this, 
                "Only CONFIRMED bookings can be checked in", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Perform check-in
        boolean success = bookingManager.checkInBooking(bookingId);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Booking checked in successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            loadBookings(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to check in booking", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Perform check-out operation
     */
    private void performCheckOut() {
        int selectedRow = bookingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to check out");
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        if (!status.equals("CHECKED_IN")) {
            JOptionPane.showMessageDialog(this, "Only CHECKED_IN bookings can be checked out");
            return;
        }
        
        boolean success = bookingManager.checkOutBooking(bookingId);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Booking checked out successfully!");
            loadBookings();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to check out booking");
        }
    }
    
    /**
     * Perform cancel operation
     */
    private void performCancel() {
        int selectedRow = bookingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel");
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String customerName = (String) tableModel.getValueAt(selectedRow, 1);
        
        // Confirm cancellation
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel booking for " + customerName + "?", 
            "Confirm Cancellation", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bookingManager.cancelBooking(bookingId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking");
            }
        }
    }
    
    /**
     * Perform search operation
     * Demonstrates: String operations, ArrayList filtering
     */
    private void performSearch() {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a customer name to search");
            return;
        }
        
        // Clear table
        tableModel.setRowCount(0);
        
        // Search bookings
        ArrayList<Booking> searchResults = bookingManager.searchBookingsByName(searchText);
        
        // Display results
        for (Booking booking : searchResults) {
            Object[] row = {
                booking.getBookingId(),
                booking.getCustomerName(),
                booking.getRoomId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getStatus(),
                "₹" + booking.getTotalAmount(),
                booking.getCustomerPhone()
            };
            tableModel.addRow(row);
        }
        
        JOptionPane.showMessageDialog(this, 
            "Found " + searchResults.size() + " bookings for '" + searchText + "'");
    }
}
