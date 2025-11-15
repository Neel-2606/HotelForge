package com.hotel.models;

import java.time.LocalDate;

public class Booking {
    
    private int bookingId;
    private int customerId;
    private int roomId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;         
    private double totalAmount;
    private LocalDate bookingDate;

    public Booking() {
        this.status = "CONFIRMED";
    }
        public Booking(int roomId, String customerName, String customerEmail, 
                   String customerPhone, String checkInDate, String checkOutDate, double totalAmount) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.checkInDate = null; 
        this.checkOutDate = null; 
        this.setCheckInDate(checkInDate);
        this.setCheckOutDate(checkOutDate);
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
    }
    
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
    
    public void displayBookingInfo() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customerName);
        System.out.println("Room: " + roomId);
        System.out.println("Check-in: " + checkInDate);
        System.out.println("Check-out: " + checkOutDate);
        System.out.println("Status: " + status);
        System.out.println("Amount: ₹" + totalAmount);
    }

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
    
    public void setCheckInDate(String checkInDate) {
        if (checkInDate != null && !checkInDate.isEmpty()) {
            try {
                this.checkInDate = LocalDate.parse(checkInDate);
            } catch (Exception e) {
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
