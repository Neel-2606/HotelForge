package com.hotel.models;

/**
 * Simple Booking class - demonstrates basic OOP concepts
 * 1. Encapsulation - private variables with public getters/setters
 * 2. Constructor - to create booking objects
 */
public class Booking {
    // Private variables (Encapsulation)
    private int bookingId;
    private int roomId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String checkInDate;    // Using String for simplicity
    private String checkOutDate;   // Using String for simplicity
    private String status;         // CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED
    private double totalAmount;
    
    // Default Constructor
    public Booking() {
        this.status = "CONFIRMED";
    }
    
    // Parameterized Constructor
    public Booking(int roomId, String customerName, String customerEmail, 
                   String customerPhone, String checkInDate, String checkOutDate, double totalAmount) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
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
    
    public String getCheckInDate() { 
        return checkInDate; 
    }
    
    public String getCheckOutDate() { 
        return checkOutDate; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public double getTotalAmount() { 
        return totalAmount; 
    }
    
    // Setter methods (to modify private variables)
    public void setBookingId(int bookingId) { 
        this.bookingId = bookingId; 
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
    
    public void setCheckInDate(String checkInDate) { 
        this.checkInDate = checkInDate; 
    }
    
    public void setCheckOutDate(String checkOutDate) { 
        this.checkOutDate = checkOutDate; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public void setTotalAmount(double totalAmount) { 
        this.totalAmount = totalAmount; 
    }
}
