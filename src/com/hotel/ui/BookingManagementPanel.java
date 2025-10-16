package com.hotel.ui;

import com.hotel.models.Booking;
import com.hotel.managers.BookingManager;
import com.hotel.exceptions.BookingConflictException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BookingManagementPanel - Main UI for admin booking management
 * Demonstrates Swing components and event handling
 */
public class BookingManagementPanel extends JPanel {
    private BookingManager bookingManager;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JButton checkInButton, checkOutButton, cancelButton, refreshButton;
    private JButton viewAvailabilityButton, todayCheckInsButton, todayCheckOutsButton;
    private JComboBox<String> statusFilter;
    private JTextField searchField;
    
    // Table columns
    private final String[] columnNames = {
        "Booking ID", "Customer Name", "Room ID", "Check-In", 
        "Check-Out", "Status", "Total Amount", "Phone", "Email"
    };
    
    public BookingManagementPanel() {
        try {
            this.bookingManager = new BookingManager(com.hotel.database.UnifiedDatabaseConnection.getConnection());
        } catch (SQLException e) {
            System.err.println("Error initializing booking manager: " + e.getMessage());
        }
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadBookings();
    }
    
    private void initializeComponents() {
        // Initialize table
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        bookingsTable = new JTable(tableModel);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.setRowHeight(25);
        
        // Initialize buttons
        checkInButton = new JButton("Check In");
        checkOutButton = new JButton("Check Out");
        cancelButton = new JButton("Cancel Booking");
        refreshButton = new JButton("Refresh");
        viewAvailabilityButton = new JButton("View Availability Grid");
        todayCheckInsButton = new JButton("Today's Check-Ins");
        todayCheckOutsButton = new JButton("Today's Check-Outs");
        
        // Initialize filters
        statusFilter = new JComboBox<>(new String[]{
            "All", "CONFIRMED", "CHECKED_IN", "CHECKED_OUT", "CANCELLED"
        });
        searchField = new JTextField(20);
        searchField.setToolTipText("Search by customer name or booking ID");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel with filters and search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Status Filter:"));
        topPanel.add(statusFilter);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(refreshButton);
        
        // Center panel with table
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        // Bottom panel with action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(viewAvailabilityButton);
        buttonPanel.add(todayCheckInsButton);
        buttonPanel.add(todayCheckOutsButton);
        
        // Add panels to main panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set border and title
        setBorder(BorderFactory.createTitledBorder("Booking Management"));
    }
    
    private void setupEventListeners() {
        // Refresh button
        refreshButton.addActionListener(e -> loadBookings());
        
        // Status filter
        statusFilter.addActionListener(e -> filterBookings());
        
        // Search field
        searchField.addActionListener(e -> filterBookings());
        
        // Check-in button
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCheckIn();
            }
        });
        
        // Check-out button
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCheckOut();
            }
        });
        
        // Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });
        
        // Availability grid button
        viewAvailabilityButton.addActionListener(e -> showAvailabilityGrid());
        
        // Today's check-ins button
        todayCheckInsButton.addActionListener(e -> showTodayCheckIns());
        
        // Today's check-outs button
        todayCheckOutsButton.addActionListener(e -> showTodayCheckOuts());
    }
    
    private void loadBookings() {
        try {
            List<Booking> bookings = bookingManager.getAllBookings();
            updateTable(bookings);
        } catch (SQLException e) {
            showErrorMessage("Error loading bookings: " + e.getMessage());
        }
    }
    
    private void updateTable(List<Booking> bookings) {
        tableModel.setRowCount(0); // Clear existing data
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Booking booking : bookings) {
            Object[] row = {
                booking.getBookingId(),
                booking.getCustomerName(),
                booking.getRoomId(),
                booking.getCheckInDate().format(formatter),
                booking.getCheckOutDate().format(formatter),
                booking.getStatus(),
                String.format("₹%.2f", booking.getTotalAmount()),
                booking.getCustomerPhone(),
                booking.getCustomerEmail()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterBookings() {
        try {
            String selectedStatus = (String) statusFilter.getSelectedItem();
            String searchText = searchField.getText().trim().toLowerCase();
            
            List<Booking> bookings;
            if ("All".equals(selectedStatus)) {
                bookings = bookingManager.getAllBookings();
            } else {
                bookings = bookingManager.getBookingsByStatus(selectedStatus);
            }
            
            // Apply search filter
            if (!searchText.isEmpty()) {
                bookings.removeIf(booking -> 
                    !booking.getCustomerName().toLowerCase().contains(searchText) &&
                    !String.valueOf(booking.getBookingId()).contains(searchText)
                );
            }
            
            updateTable(bookings);
        } catch (SQLException e) {
            showErrorMessage("Error filtering bookings: " + e.getMessage());
        }
    }
    
    private void performCheckIn() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarningMessage("Please select a booking to check in.");
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        if (!"CONFIRMED".equals(status)) {
            showWarningMessage("Only confirmed bookings can be checked in.");
            return;
        }
        
        try {
            boolean success = bookingManager.checkInBooking(bookingId);
            if (success) {
                showSuccessMessage("Booking checked in successfully!");
                loadBookings();
            } else {
                showErrorMessage("Failed to check in booking. Please try again.");
            }
        } catch (SQLException e) {
            showErrorMessage("Error checking in booking: " + e.getMessage());
        }
    }
    
    private void performCheckOut() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarningMessage("Please select a booking to check out.");
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        if (!"CHECKED_IN".equals(status)) {
            showWarningMessage("Only checked-in bookings can be checked out.");
            return;
        }
        
        try {
            boolean success = bookingManager.checkOutBooking(bookingId);
            if (success) {
                showSuccessMessage("Booking checked out successfully!");
                loadBookings();
            } else {
                showErrorMessage("Failed to check out booking. Please try again.");
            }
        } catch (SQLException e) {
            showErrorMessage("Error checking out booking: " + e.getMessage());
        }
    }
    
    private void cancelBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarningMessage("Please select a booking to cancel.");
            return;
        }
        
        int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String customerName = (String) tableModel.getValueAt(selectedRow, 1);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        if ("CANCELLED".equals(status) || "CHECKED_OUT".equals(status)) {
            showWarningMessage("This booking cannot be cancelled.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel booking for " + customerName + "?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = bookingManager.cancelBooking(bookingId);
                if (success) {
                    showSuccessMessage("Booking cancelled successfully!");
                    loadBookings();
                } else {
                    showErrorMessage("Failed to cancel booking. Please try again.");
                }
            } catch (SQLException e) {
                showErrorMessage("Error cancelling booking: " + e.getMessage());
            }
        }
    }
    
    private void showAvailabilityGrid() {
        try {
            Map<LocalDate, Set<Integer>> availability = bookingManager.getAvailabilityGrid();
            AvailabilityGridDialog dialog = new AvailabilityGridDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), availability);
            dialog.setVisible(true);
        } catch (SQLException e) {
            showErrorMessage("Error loading availability grid: " + e.getMessage());
        }
    }
    
    private void showTodayCheckIns() {
        try {
            List<Booking> todayCheckIns = bookingManager.getTodayCheckIns();
            if (todayCheckIns.isEmpty()) {
                showInfoMessage("No check-ins scheduled for today.");
            } else {
                updateTable(todayCheckIns);
                showInfoMessage("Showing " + todayCheckIns.size() + " check-ins for today.");
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading today's check-ins: " + e.getMessage());
        }
    }
    
    private void showTodayCheckOuts() {
        try {
            List<Booking> todayCheckOuts = bookingManager.getTodayCheckOuts();
            if (todayCheckOuts.isEmpty()) {
                showInfoMessage("No check-outs scheduled for today.");
            } else {
                updateTable(todayCheckOuts);
                showInfoMessage("Showing " + todayCheckOuts.size() + " check-outs for today.");
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading today's check-outs: " + e.getMessage());
        }
    }
    
    // Utility methods for showing messages
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
