package com.hotel.managers;

import com.hotel.models.Booking;
import com.hotel.exceptions.BookingConflictException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * BookingManager class for handling all booking operations
 * Demonstrates Collections (HashMap), Date & Time API, Exception Handling
 */
public class BookingManager {
    private Connection connection;
    private HashMap<Integer, List<Booking>> roomBookingsCache; // Collections: HashMap
    
    public BookingManager(Connection connection) {
        this.connection = connection;
        this.roomBookingsCache = new HashMap<>();
    }
    
    /**
     * Get all bookings from database
     * @return List of all bookings
     */
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings ORDER BY check_in_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("room_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getString("customer_phone"),
                    rs.getDate("check_in_date").toLocalDate(),
                    rs.getDate("check_out_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getDouble("total_amount"),
                    rs.getDate("booking_date").toLocalDate()
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }
    
    /**
     * Check room availability for given dates
     * Demonstrates Date & Time API usage
     */
    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) throws SQLException {
        String query = "SELECT COUNT(*) FROM bookings WHERE room_id = ? AND status IN ('CONFIRMED', 'CHECKED_IN') " +
                      "AND NOT (check_out_date <= ? OR check_in_date >= ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, Date.valueOf(checkIn));
            stmt.setDate(3, Date.valueOf(checkOut));
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) == 0;
        }
    }
    
    /**
     * Get availability grid for next 30 days
     * Demonstrates Date & Time API and Collections
     */
    public Map<LocalDate, Set<Integer>> getAvailabilityGrid() throws SQLException {
        Map<LocalDate, Set<Integer>> availability = new HashMap<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(30);
        
        // Initialize all dates with all rooms available
        Set<Integer> allRooms = getAllRoomIds();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            availability.put(date, new HashSet<>(allRooms));
        }
        
        // Remove booked rooms from availability
        String query = "SELECT room_id, check_in_date, check_out_date FROM bookings " +
                      "WHERE status IN ('CONFIRMED', 'CHECKED_IN') " +
                      "AND check_out_date > ? AND check_in_date <= ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                LocalDate bookingStart = rs.getDate("check_in_date").toLocalDate();
                LocalDate bookingEnd = rs.getDate("check_out_date").toLocalDate();
                
                // Remove room from availability for booking period
                LocalDate current = bookingStart.isBefore(startDate) ? startDate : bookingStart;
                LocalDate last = bookingEnd.isAfter(endDate) ? endDate : bookingEnd.minusDays(1);
                
                while (!current.isAfter(last)) {
                    availability.get(current).remove(roomId);
                    current = current.plusDays(1);
                }
            }
        }
        
        return availability;
    }
    
    /**
     * Validate booking dates and prevent overlapping bookings
     * Demonstrates Exception Handling
     */
    public void validateBooking(int roomId, LocalDate checkIn, LocalDate checkOut) 
            throws BookingConflictException {
        
        // Validate dates
        if (checkIn.isBefore(LocalDate.now())) {
            throw new BookingConflictException("Check-in date cannot be in the past");
        }
        
        if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
            throw new BookingConflictException("Check-out date must be after check-in date");
        }
        
        // Check for overlapping bookings
        try {
            if (!isRoomAvailable(roomId, checkIn, checkOut)) {
                throw new BookingConflictException(
                    "Room " + roomId + " is not available for the selected dates");
            }
        } catch (SQLException e) {
            throw new BookingConflictException("Error checking room availability", e);
        }
    }
    
    /**
     * Check-in a booking
     */
    public boolean checkInBooking(int bookingId) throws SQLException {
        String query = "UPDATE bookings SET status = 'CHECKED_IN' WHERE booking_id = ? AND status = 'CONFIRMED'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Check-out a booking
     */
    public boolean checkOutBooking(int bookingId) throws SQLException {
        String query = "UPDATE bookings SET status = 'CHECKED_OUT' WHERE booking_id = ? AND status = 'CHECKED_IN'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Cancel a booking
     */
    public boolean cancelBooking(int bookingId) throws SQLException {
        String query = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ? AND status IN ('CONFIRMED', 'CHECKED_IN')";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Get bookings by status
     */
    public List<Booking> getBookingsByStatus(String status) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE status = ? ORDER BY check_in_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("room_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getString("customer_phone"),
                    rs.getDate("check_in_date").toLocalDate(),
                    rs.getDate("check_out_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getDouble("total_amount"),
                    rs.getDate("booking_date").toLocalDate()
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }
    
    /**
     * Get all room IDs from database
     */
    private Set<Integer> getAllRoomIds() throws SQLException {
        Set<Integer> roomIds = new HashSet<>();
        String query = "SELECT room_id FROM rooms";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                roomIds.add(rs.getInt("room_id"));
            }
        }
        return roomIds;
    }
    
    /**
     * Get bookings for today's check-ins
     */
    public List<Booking> getTodayCheckIns() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE check_in_date = ? AND status = 'CONFIRMED'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("room_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getString("customer_phone"),
                    rs.getDate("check_in_date").toLocalDate(),
                    rs.getDate("check_out_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getDouble("total_amount"),
                    rs.getDate("booking_date").toLocalDate()
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }
    
    /**
     * Get bookings for today's check-outs
     */
    public List<Booking> getTodayCheckOuts() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE check_out_date = ? AND status = 'CHECKED_IN'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("room_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getString("customer_phone"),
                    rs.getDate("check_in_date").toLocalDate(),
                    rs.getDate("check_out_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getDouble("total_amount"),
                    rs.getDate("booking_date").toLocalDate()
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }
}
