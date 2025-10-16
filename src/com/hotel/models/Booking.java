package com.hotel.models;

import java.time.LocalDate;

/**
 * Simple Booking class - demonstrates basic OOP concepts
 * 1. Encapsulation - private variables with public getters/setters
 * 2. Constructor - to create booking objects
 */
public class Booking {
    // Private variables (Encapsulation)
    private int bookingId;
    private int customerId;
    private int roomId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;         // CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED
    private double totalAmount;
    private LocalDate bookingDate;

    // Default Constructor
    public Booking() {
        this.status = "CONFIRMED";
    }
    
    // Parameterized Constructor for simple version
    public Booking(int roomId, String customerName, String customerEmail, 
                   String customerPhone, String checkInDate, String checkOutDate, double totalAmount) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.checkInDate = null; // Will be set using setter
        this.checkOutDate = null; // Will be set using setter
        this.setCheckInDate(checkInDate);
        this.setCheckOutDate(checkOutDate);
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
    }
    
    // Full constructor for BookingManager
    public Booking(int bookingId, int customerId, int roomId, 
                  String customerName, String customerEmail, String customerPhone,
                  LocalDate checkInDate, LocalDate checkOutDate, 
                  String status, double totalAmount, LocalDate bookingDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
    }
    
    // Simple method to display booking info
    public void displayBookingInfo() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customerName);
        System.out.println("Room: " + roomId);
        System.out.println("Check-in: " + checkInDate);
        System.out.println("Check-out: " + checkOutDate);
        System.out.println("Status: " + status);
        System.out.println("Amount: ₹" + totalAmount);
    }

    // Getter methods (to access private variables)
    public int getBookingId() { 
        return bookingId; 
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public int getRoomId() { 
        return roomId; 
    }
    
    public String getCustomerName() { 
        return customerName; 
    }
    
    public String getCustomerEmail() { 
        return customerEmail; 
    }
    
    public String getCustomerPhone() { 
        return customerPhone; 
    }
    
    public LocalDate getCheckInDate() { 
        return checkInDate; 
    }
    
    public LocalDate getCheckOutDate() { 
        return checkOutDate; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public double getTotalAmount() { 
        return totalAmount; 
    }
    
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    
    // Setter methods (to modify private variables)
    public void setBookingId(int bookingId) { 
        this.bookingId = bookingId; 
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setRoomId(int roomId) { 
        this.roomId = roomId; 
    }
    
    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    }
    
    public void setCustomerEmail(String customerEmail) { 
        this.customerEmail = customerEmail; 
    }
    
    public void setCustomerPhone(String customerPhone) { 
        this.customerPhone = customerPhone; 
    }
    
    // Overloaded methods to handle both String and LocalDate
    public void setCheckInDate(String checkInDate) {
        if (checkInDate != null && !checkInDate.isEmpty()) {
            try {
                this.checkInDate = LocalDate.parse(checkInDate);
            } catch (Exception e) {
                // Handle parsing error - use a default date or current date
                this.checkInDate = LocalDate.now();
            }
        }
    }
    
    public void setCheckInDate(LocalDate checkInDate) { 
        this.checkInDate = checkInDate; 
    }
    
    public void setCheckOutDate(String checkOutDate) {
        if (checkOutDate != null && !checkOutDate.isEmpty()) {
            try {
                this.checkOutDate = LocalDate.parse(checkOutDate);
            } catch (Exception e) {
                // Handle parsing error - use check-in date + 1 day as default
                this.checkOutDate = (this.checkInDate != null) ? 
                    this.checkInDate.plusDays(1) : LocalDate.now().plusDays(1);
            }
        }
    }
    
    public void setCheckOutDate(LocalDate checkOutDate) { 
        this.checkOutDate = checkOutDate; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public void setTotalAmount(double totalAmount) { 
        this.totalAmount = totalAmount; 
    }
    
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
