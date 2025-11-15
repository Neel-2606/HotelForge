package com.hotel.ui;

import com.hotel.managers.SimpleBookingManager;
import com.hotel.models.Booking;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SimpleBookingPanel extends JPanel {
    
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton cancelButton;
    private JTextField searchField;
    private JButton searchButton;
    
    private SimpleBookingManager bookingManager;
    
    private String[] columnNames = {
        "Booking ID", "Customer Name", "Room ID", "Check-In", 
        "Check-Out", "Status", "Amount", "Phone"
    };
    
    public SimpleBookingPanel() {
        try {
            this.bookingManager = new SimpleBookingManager(com.hotel.database.UnifiedDatabaseConnection.getConnection());
        } catch (Exception e) {
            System.err.println("Error initializing booking manager: " + e.getMessage());
        }
        
        createComponents();
        setupLayout();
    }
    
    public SimpleBookingPanel(Connection connection) {
        try {
            this.bookingManager = new SimpleBookingManager(connection);
        } catch (Exception e) {
            System.err.println("Error initializing booking manager: " + e.getMessage());
        }
        
        createComponents();
        setupLayout();
        addEventListeners();
        loadBookings();
    }
    
    private void createComponents() {
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshButton = new JButton("Refresh");
        checkInButton = new JButton("Check In");
        checkOutButton = new JButton("Check Out");
        cancelButton = new JButton("Cancel Booking");
        searchButton = new JButton("Search");
        
        searchField = new JTextField(20);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Search Customer:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(refreshButton);
        
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(checkInButton);
        bottomPanel.add(checkOutButton);
        bottomPanel.add(cancelButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void addEventListeners() {
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookings();
            }
        });
        
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCheckIn();
            }
        });
        
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCheckOut();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCancel();
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }
    
    private void loadBookings() {
        tableModel.setRowCount(0);
        
        ArrayList<Booking> bookings = bookingManager.getAllBookings();
        
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
        
        JOptionPane.showMessageDialog(this, 
            "Loaded " + bookings.size() + " bookings", 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void performCheckIn() {
        int selectedRow = bookingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a booking to check in", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        if (!status.equals("CONFIRMED")) {
            JOptionPane.showMessageDialog(this, 
                "Only CONFIRMED bookings can be checked in", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = bookingManager.checkInBooking(bookingId);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Booking checked in successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            loadBookings(); 
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to check in booking", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
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
    
    private void performCancel() {
        int selectedRow = bookingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel");
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String customerName = (String) tableModel.getValueAt(selectedRow, 1);
        
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
    
    private void performSearch() {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a customer name to search");
            return;
        }
        
        tableModel.setRowCount(0);
        
        ArrayList<Booking> searchResults = bookingManager.searchBookingsByName(searchText);
        
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
