-- Simple Hotel Management Database Schema
-- For 1st Year CSE Students

-- Create database
CREATE DATABASE IF NOT EXISTS hotel_management;
USE hotel_management;

-- Simple Rooms table
CREATE TABLE IF NOT EXISTS rooms (
    room_id INT PRIMARY KEY,
    room_number VARCHAR(10) NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DECIMAL(8,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE'
);

-- Simple Bookings table (main table for our project)
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(15) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    total_amount DECIMAL(8,2) NOT NULL
);

-- Insert sample rooms (simple data)
INSERT INTO rooms (room_id, room_number, room_type, price_per_night, status) VALUES
(101, 'R101', 'Standard', 2500.00, 'AVAILABLE'),
(102, 'R102', 'Standard', 2500.00, 'AVAILABLE'),
(103, 'R103', 'Deluxe', 3500.00, 'AVAILABLE'),
(104, 'R104', 'Deluxe', 3500.00, 'AVAILABLE'),
(105, 'R105', 'Suite', 5000.00, 'AVAILABLE');

-- Insert sample bookings (simple test data)
INSERT INTO bookings (room_id, customer_name, customer_email, customer_phone, 
                     check_in_date, check_out_date, status, total_amount) VALUES
(101, 'John Doe', 'john@email.com', '9876543210', '2024-10-20', '2024-10-23', 'CONFIRMED', 7500.00),
(102, 'Jane Smith', 'jane@email.com', '9876543211', '2024-10-21', '2024-10-24', 'CONFIRMED', 7500.00),
(103, 'Bob Johnson', 'bob@email.com', '9876543212', '2024-10-19', '2024-10-21', 'CHECKED_IN', 7000.00),
(104, 'Alice Brown', 'alice@email.com', '9876543213', '2024-10-15', '2024-10-18', 'CHECKED_OUT', 10500.00),
(105, 'Charlie Wilson', 'charlie@email.com', '9876543214', '2024-10-22', '2024-10-25', 'CONFIRMED', 15000.00);

-- Show all data (for verification)
SELECT 'Rooms Table:' as Info;
SELECT * FROM rooms;

SELECT 'Bookings Table:' as Info;
SELECT * FROM bookings;
