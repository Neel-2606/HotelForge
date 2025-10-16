-- Hotel Management System Database Schema
-- Member 4 - Booking Management Module

-- Create database
CREATE DATABASE IF NOT EXISTS hotel_management;
USE hotel_management;

-- Rooms table (needed for booking management)
CREATE TABLE IF NOT EXISTS rooms (
    room_id INT PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    capacity INT NOT NULL,
    amenities TEXT,
    status VARCHAR(20) DEFAULT 'AVAILABLE'
);

-- Users table (for authentication)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bookings table (main table for Member 4)
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    room_id INT,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(15) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    total_amount DECIMAL(10,2) NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    special_requests TEXT,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id),
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

-- Insert sample rooms
INSERT INTO rooms (room_id, room_number, room_type, price_per_night, capacity, amenities, status) VALUES
(101, 'R101', 'Standard', 2500.00, 2, 'AC, TV, WiFi', 'AVAILABLE'),
(102, 'R102', 'Standard', 2500.00, 2, 'AC, TV, WiFi', 'AVAILABLE'),
(103, 'R103', 'Deluxe', 3500.00, 3, 'AC, TV, WiFi, Mini Bar', 'AVAILABLE'),
(104, 'R104', 'Deluxe', 3500.00, 3, 'AC, TV, WiFi, Mini Bar', 'AVAILABLE'),
(105, 'R105', 'Suite', 5000.00, 4, 'AC, TV, WiFi, Mini Bar, Jacuzzi', 'AVAILABLE'),
(106, 'R106', 'Suite', 5000.00, 4, 'AC, TV, WiFi, Mini Bar, Jacuzzi', 'AVAILABLE'),
(107, 'R107', 'Standard', 2500.00, 2, 'AC, TV, WiFi', 'AVAILABLE'),
(108, 'R108', 'Deluxe', 3500.00, 3, 'AC, TV, WiFi, Mini Bar', 'AVAILABLE'),
(109, 'R109', 'Standard', 2500.00, 2, 'AC, TV, WiFi', 'AVAILABLE'),
(110, 'R110', 'Suite', 5000.00, 4, 'AC, TV, WiFi, Mini Bar, Jacuzzi', 'AVAILABLE');

-- Insert sample users
INSERT INTO users (username, password, role, full_name, email, phone) VALUES
('admin', SHA2('admin123', 256), 'ADMIN', 'Hotel Administrator', 'admin@hotel.com', '9876543210'),
('customer1', SHA2('pass123', 256), 'CUSTOMER', 'John Doe', 'john@email.com', '9876543211'),
('customer2', SHA2('pass123', 256), 'CUSTOMER', 'Jane Smith', 'jane@email.com', '9876543212');

-- Insert sample bookings for testing
INSERT INTO bookings (customer_id, room_id, customer_name, customer_email, customer_phone, 
                     check_in_date, check_out_date, status, total_amount, special_requests) VALUES
(2, 101, 'John Doe', 'john@email.com', '9876543211', 
 CURDATE(), DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'CONFIRMED', 7500.00, 'Late check-in requested'),
(3, 103, 'Jane Smith', 'jane@email.com', '9876543212', 
 DATE_ADD(CURDATE(), INTERVAL 1 DAY), DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'CONFIRMED', 10500.00, 'Extra towels'),
(2, 105, 'John Doe', 'john@email.com', '9876543211', 
 DATE_ADD(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'CONFIRMED', 10000.00, 'Anniversary celebration'),
(3, 102, 'Jane Smith', 'jane@email.com', '9876543212', 
 DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'CHECKED_OUT', 2500.00, 'Business trip');

-- Create indexes for better performance
CREATE INDEX idx_bookings_dates ON bookings(check_in_date, check_out_date);
CREATE INDEX idx_bookings_status ON bookings(status);
CREATE INDEX idx_bookings_room ON bookings(room_id);
CREATE INDEX idx_bookings_customer ON bookings(customer_id);

-- Views for common queries
CREATE VIEW active_bookings AS
SELECT b.*, r.room_number, r.room_type 
FROM bookings b 
JOIN rooms r ON b.room_id = r.room_id 
WHERE b.status IN ('CONFIRMED', 'CHECKED_IN');

CREATE VIEW todays_checkins AS
SELECT b.*, r.room_number, r.room_type 
FROM bookings b 
JOIN rooms r ON b.room_id = r.room_id 
WHERE b.check_in_date = CURDATE() AND b.status = 'CONFIRMED';

CREATE VIEW todays_checkouts AS
SELECT b.*, r.room_number, r.room_type 
FROM bookings b 
JOIN rooms r ON b.room_id = r.room_id 
WHERE b.check_out_date = CURDATE() AND b.status = 'CHECKED_IN';
