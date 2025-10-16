package com.hotel.managers;

import com.hotel.models.Booking;
import java.sql.*;
import java.util.ArrayList;

/**
 * Simple BookingManager class - demonstrates basic concepts
 * 1. ArrayList (Collections) - to store bookings
 * 2. Basic JDBC operations
 * 3. Simple exception handling with try-catch
 */
public class SimpleBookingManager {
    private Connection connection;
    private ArrayList<Booking> bookingList; // Using ArrayList (Collections)
    
    // Constructor
    public SimpleBookingManager(Connection connection) {
        this.connection = connection;
        this.bookingList = new ArrayList<>(); // Initialize ArrayList
    }
    
    /**
     * Get all bookings from database
     * Demonstrates: JDBC, ArrayList, Exception Handling
     */
    public ArrayList<Booking> getAllBookings() {
        bookingList.clear(); // Clear existing data
        String query = "SELECT * FROM bookings ORDER BY booking_id";
        
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                // Create new booking object
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setCustomerEmail(rs.getString("customer_email"));
                booking.setCustomerPhone(rs.getString("customer_phone"));
                booking.setCheckInDate(rs.getString("check_in_date"));
                booking.setCheckOutDate(rs.getString("check_out_date"));
                booking.setStatus(rs.getString("status"));
                booking.setTotalAmount(rs.getDouble("total_amount"));
                
                // Add to ArrayList
                bookingList.add(booking);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.out.println("Error getting bookings: " + e.getMessage());
        }
        
        return bookingList;
    }
    
    /**
     * Check-in a booking
     * Demonstrates: Basic SQL UPDATE operation
     */
    public boolean checkInBooking(int bookingId) {
        String query = "UPDATE bookings SET status = 'CHECKED_IN' WHERE booking_id = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, bookingId);
            
            int rowsUpdated = stmt.executeUpdate();
            stmt.close();
            
            return rowsUpdated > 0; // Return true if update successful
            
        } catch (SQLException e) {
            System.out.println("Error checking in booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check-out a booking
     * Demonstrates: Basic SQL UPDATE operation
     */
    public boolean checkOutBooking(int bookingId) {
        String query = "UPDATE bookings SET status = 'CHECKED_OUT' WHERE booking_id = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, bookingId);
            
            int rowsUpdated = stmt.executeUpdate();
            stmt.close();
            
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error checking out booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cancel a booking
     * Demonstrates: Basic SQL UPDATE operation
     */
    public boolean cancelBooking(int bookingId) {
        String query = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, bookingId);
            
            int rowsUpdated = stmt.executeUpdate();
            stmt.close();
            
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Search bookings by customer name
     * Demonstrates: ArrayList searching, String methods
     */
    public ArrayList<Booking> searchBookingsByName(String customerName) {
        ArrayList<Booking> searchResults = new ArrayList<>();
        
        // Get all bookings first
        getAllBookings();
        
        // Search through ArrayList
        for (int i = 0; i < bookingList.size(); i++) {
            Booking booking = bookingList.get(i);
            
            // Simple string comparison (case-insensitive)
            if (booking.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) {
                searchResults.add(booking);
            }
        }
        
        return searchResults;
    }
    
    /**
     * Get bookings by status
     * Demonstrates: ArrayList filtering
     */
    public ArrayList<Booking> getBookingsByStatus(String status) {
        ArrayList<Booking> filteredBookings = new ArrayList<>();
        
        // Get all bookings first
        getAllBookings();
        
        // Filter by status
        for (Booking booking : bookingList) { // Enhanced for loop
            if (booking.getStatus().equals(status)) {
                filteredBookings.add(booking);
            }
        }
        
        return filteredBookings;
    }
    
    /**
     * Count total bookings
     * Demonstrates: ArrayList size() method
     */
    public int getTotalBookingsCount() {
        getAllBookings();
        return bookingList.size();
    }
    
    /**
     * Display all bookings (for testing)
     * Demonstrates: ArrayList iteration
     */
    public void displayAllBookings() {
        getAllBookings();
        
        System.out.println("=== All Bookings ===");
        for (int i = 0; i < bookingList.size(); i++) {
            System.out.println("Booking " + (i + 1) + ":");
            bookingList.get(i).displayBookingInfo();
            System.out.println("---");
        }
    }
}
