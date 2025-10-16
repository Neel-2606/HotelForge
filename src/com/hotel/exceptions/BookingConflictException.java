package com.hotel.exceptions;

/**
 * Custom exception for booking conflicts
 * Demonstrates Exception Handling concepts
 */
public class BookingConflictException extends Exception {
    
    public BookingConflictException(String message) {
        super(message);
    }
    
    public BookingConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
