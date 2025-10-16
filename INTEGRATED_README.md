# Hotel Management System - Integrated Solution

## Overview
This is a comprehensive Hotel Management System built in Java that integrates multiple modules including Authentication, Room Management, and Booking Management. The system demonstrates core Java concepts including OOP, Collections, Exception Handling, JDBC, and Swing GUI.

## Features

### 🔐 Authentication Module
- User login and registration
- Role-based access control (Admin/Customer)
- Session management
- Password validation
- Security features (login attempt limits)

### 🏨 Room Management Module
- View all hotel rooms
- Room type management (Standard, Deluxe, Suite)
- Amenity management
- Room status tracking
- Price management

### 📅 Booking Management Module
- Create new bookings
- View all bookings (Admin) / My bookings (Customer)
- Check-in/Check-out operations
- Booking cancellation
- Search and filter bookings
- 30-day availability calendar

### 📊 Dashboard & Reports
- System overview dashboard
- Quick statistics
- User management (Admin only)
- Reports and analytics (Admin only)

## Technology Stack

- **Language**: Java SE 8+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J 8.0.33
- **Build Tool**: Command Line (Batch scripts provided)

## Project Structure

```
HotelForge/
├── src/
│   ├── com/hotel/
│   │   ├── database/
│   │   │   ├── UnifiedDatabaseConnection.java
│   │   │   ├── DatabaseConnection.java
│   │   │   └── SimpleConnection.java
│   │   ├── models/
│   │   │   ├── User.java
│   │   │   └── Booking.java
│   │   ├── services/
│   │   │   └── AuthenticationService.java
│   │   ├── managers/
│   │   │   ├── BookingManager.java
│   │   │   └── SimpleBookingManager.java
│   │   ├── ui/
│   │   │   ├── UnifiedLoginForm.java
│   │   │   ├── UnifiedRegistrationForm.java
│   │   │   ├── HotelManagementMainApp.java
│   │   │   ├── BookingManagementPanel.java
│   │   │   ├── SimpleBookingPanel.java
│   │   │   └── AvailabilityGridDialog.java
│   │   ├── exceptions/
│   │   │   └── BookingConflictException.java
│   │   └── main/
│   │       └── HotelManagementApplication.java
│   └── main/
│       ├── model/
│       │   ├── Room.java
│       │   ├── RoomType.java
│       │   └── Amenity.java
│       ├── dao/
│       │   └── RoomDAO.java
│       ├── ui/
│       │   └── RoomManagementPanel.java
│       └── Main.java
├── database/
│   └── schema.sql
├── lib/
│   └── mysql-connector-j-8.0.33.jar
├── compile_and_run.bat
├── setup_database.bat
└── README files...
```

## Setup Instructions

### 1. Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server 8.0 or higher
- Windows OS (for batch scripts) or adapt for your OS

### 2. Database Setup

1. **Install MySQL Server** if not already installed
2. **Start MySQL Service**
3. **Run the database setup script**:
   ```bash
   setup_database.bat
   ```
   - Enter your MySQL username (default: root)
   - Enter your MySQL password
   - The script will create the database and tables automatically

### 3. Configure Database Connection

Update the database credentials in `src/com/hotel/database/UnifiedDatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/hotel_management";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password"; // Update this
```

### 4. Compile and Run

Simply run the provided batch script:
```bash
compile_and_run.bat
```

This script will:
- Clean previous builds
- Compile all Java source files
- Set up the classpath correctly
- Run the application

### 5. Alternative Manual Compilation

If you prefer manual compilation:

```bash
# Create bin directory
mkdir bin

# Compile
javac -cp "lib\mysql-connector-j-8.0.33.jar;src" -d bin -sourcepath src src\com\hotel\main\HotelManagementApplication.java

# Run
java -cp "lib\mysql-connector-j-8.0.33.jar;bin" com.hotel.main.HotelManagementApplication
```

## Usage

### First Time Login
1. Run the application
2. Use the default admin credentials:
   - **Username**: admin
   - **Password**: admin123

### Creating New Users
1. Click "Register" on the login form
2. Fill in the required information
3. Select role (Customer/Admin)
4. Click "Register"

### Navigation
- **Dashboard**: Overview of system statistics
- **Room Management**: Manage hotel rooms and amenities
- **Booking Management**: Handle reservations and check-ins
- **User Management**: Admin-only user administration
- **Reports**: System analytics and reports

## Core Java Concepts Demonstrated

### 1. Object-Oriented Programming
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: UI components extending Swing classes
- **Polymorphism**: Interface implementations
- **Abstraction**: Service layer abstractions

### 2. Collections Framework
- `ArrayList` for dynamic lists
- `HashMap` for caching and lookups
- `TreeSet` for sorted collections
- `HashSet` for unique collections

### 3. Exception Handling
- Custom exceptions (`BookingConflictException`)
- Try-catch blocks for database operations
- Resource management with try-with-resources

### 4. JDBC Operations
- Database connection management
- Prepared statements for SQL injection prevention
- Result set processing
- Transaction handling

### 5. Swing GUI Components
- `JFrame`, `JPanel`, `JButton`, `JTable`
- Layout managers (`BorderLayout`, `GridBagLayout`, `FlowLayout`)
- Event handling with `ActionListener`
- Custom dialogs and forms

### 6. Date & Time API
- `LocalDate` for date operations
- `ChronoUnit` for date calculations
- Date validation and formatting

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Rooms Table
```sql
CREATE TABLE rooms (
    room_id INT PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    capacity INT NOT NULL,
    amenities TEXT,
    status VARCHAR(20) DEFAULT 'AVAILABLE'
);
```

### Bookings Table
```sql
CREATE TABLE bookings (
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
```

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Ensure MySQL server is running
   - Check username/password in `UnifiedDatabaseConnection.java`
   - Verify database `hotel_management` exists

2. **Compilation Errors**
   - Ensure JDK is properly installed
   - Check JAVA_HOME environment variable
   - Verify MySQL connector JAR is in lib/ directory

3. **ClassNotFoundException**
   - Ensure MySQL connector JAR is in classpath
   - Check if all Java files compiled successfully

4. **GUI Not Displaying**
   - Check if running on a system with GUI support
   - Verify Swing libraries are available

### Error Messages

- **"MySQL JDBC Driver not found"**: Add mysql-connector-j-8.0.33.jar to classpath
- **"Database connection test failed"**: Check MySQL server status and credentials
- **"Too many failed login attempts"**: Restart application or use correct credentials

## Sample Data

The system comes with pre-loaded sample data:

### Sample Users
- **Admin**: username=admin, password=admin123
- **Customer 1**: username=customer1, password=pass123
- **Customer 2**: username=customer2, password=pass123

### Sample Rooms
- 10 rooms with different types (Standard, Deluxe, Suite)
- Various amenities and pricing
- All initially available

### Sample Bookings
- Several test bookings with different statuses
- Past, current, and future bookings for testing

## Future Enhancements

1. **Advanced Features**
   - Online payment integration
   - Email notifications
   - SMS alerts
   - Mobile app integration

2. **Technical Improvements**
   - Connection pooling
   - Caching mechanisms
   - RESTful API
   - Web interface

3. **Business Features**
   - Loyalty programs
   - Discount management
   - Inventory management
   - Staff management

## Contributing

This project is part of a college assignment. For educational purposes:

1. Fork the repository
2. Create feature branches
3. Follow Java coding conventions
4. Add comprehensive comments
5. Test thoroughly before submitting

## License

This project is for educational purposes only.

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review console output for error messages
3. Verify database setup and connection
4. Ensure all prerequisites are met

---

**Hotel Management System - Integrated Solution**  
*Demonstrating Core Java Concepts in a Real-World Application*
