# Hotel Management System - Quick Start Guide

## ⚠️ Important: Compilation Issues Resolution

The IDE is showing compilation errors due to missing Java runtime classes. This is a common issue that can be resolved by following these steps:

## 🔧 Fix Compilation Issues

### Step 1: Check Java Installation
```bash
java -version
javac -version
```
Both should show Java 8 or higher.

### Step 2: Set JAVA_HOME (if not set)
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.7
set PATH=%JAVA_HOME%\bin;%PATH%

# Or add to system environment variables permanently
```

### Step 3: Refresh IDE Project
1. Close your IDE
2. Delete any `.classpath` or `.project` files
3. Reopen the project
4. Refresh/rebuild the project

## 🚀 Quick Setup (3 Steps)

### 1. Database Setup
```bash
# Run this in MySQL command line or MySQL Workbench
mysql -u root -p < database/schema.sql
```

### 2. Update Database Password
Edit `src/com/hotel/database/UnifiedDatabaseConnection.java`:
```java
private static final String PASSWORD = "your_actual_mysql_password";
```

### 3. Compile and Run
```bash
# Method 1: Use the batch script
compile_and_run.bat

# Method 2: Manual compilation
javac -cp "lib\mysql-connector-j-8.0.33.jar;src" -d bin src\com\hotel\main\HotelManagementApplication.java
java -cp "lib\mysql-connector-j-8.0.33.jar;bin" com.hotel.main.HotelManagementApplication
```

## 🎯 What You Get

### ✅ Integrated Features
- **Login System**: Username/password authentication
- **Role-based Access**: Admin and Customer roles
- **Room Management**: View and manage hotel rooms
- **Booking System**: Create, view, and manage bookings
- **Dashboard**: System overview and statistics

### 🔑 Default Login Credentials
- **Username**: admin
- **Password**: admin123

### 📊 Sample Data Included
- 10 hotel rooms (Standard, Deluxe, Suite)
- 3 user accounts (1 admin, 2 customers)
- 4 sample bookings for testing

## 🏗️ Project Architecture

```
Hotel Management System
├── Authentication Module (Login/Registration)
├── Room Management Module (View/Manage Rooms)
├── Booking Management Module (Reservations)
├── Dashboard Module (Statistics/Overview)
└── Database Layer (MySQL with JDBC)
```

## 🔍 Core Java Concepts Demonstrated

1. **Object-Oriented Programming**
   - Classes and Objects
   - Encapsulation (private fields, public methods)
   - Inheritance (extending JPanel, JFrame)
   - Polymorphism (interface implementations)

2. **Collections Framework**
   - ArrayList for dynamic lists
   - HashMap for key-value storage
   - TreeSet for sorted collections

3. **Exception Handling**
   - Try-catch blocks
   - Custom exceptions
   - Resource management

4. **JDBC Database Operations**
   - Connection management
   - Prepared statements
   - Result set processing

5. **Swing GUI Programming**
   - JFrame, JPanel, JButton, JTable
   - Layout managers
   - Event handling

## 🐛 Troubleshooting

### Problem: "Cannot resolve java.lang.String"
**Solution**: IDE classpath issue. Refresh project or restart IDE.

### Problem: "Database connection failed"
**Solution**: 
1. Start MySQL server
2. Check password in `UnifiedDatabaseConnection.java`
3. Ensure database exists: `CREATE DATABASE hotel_management;`

### Problem: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution**: Ensure `mysql-connector-j-8.0.33.jar` is in the `lib/` folder.

### Problem: Compilation errors
**Solution**: 
```bash
# Clean and recompile
rmdir /s bin
mkdir bin
javac -cp "lib\mysql-connector-j-8.0.33.jar;src" -d bin src\com\hotel\main\*.java
```

## 📁 Key Files Created/Modified

### New Integration Files:
- `src/com/hotel/database/UnifiedDatabaseConnection.java` - Centralized DB connection
- `src/com/hotel/models/User.java` - User model for authentication
- `src/com/hotel/services/AuthenticationService.java` - Login/registration logic
- `src/com/hotel/ui/UnifiedLoginForm.java` - Main login interface
- `src/com/hotel/ui/UnifiedRegistrationForm.java` - User registration
- `src/com/hotel/ui/HotelManagementMainApp.java` - Main application window
- `src/com/hotel/main/HotelManagementApplication.java` - Application entry point

### Build Scripts:
- `compile_and_run.bat` - Automated build and run
- `setup_database.bat` - Database setup script

### Documentation:
- `INTEGRATED_README.md` - Comprehensive documentation
- `QUICK_START_GUIDE.md` - This quick start guide

## 🎓 Educational Value

This project demonstrates:
- **Real-world application structure**
- **Industry-standard practices**
- **Multiple design patterns**
- **Database integration**
- **User interface design**
- **Error handling and validation**

## 📞 Support

If you encounter issues:
1. Check this troubleshooting guide
2. Verify all prerequisites are met
3. Check console output for specific error messages
4. Ensure MySQL server is running and accessible

---

**Ready to run your integrated Hotel Management System!** 🏨✨
