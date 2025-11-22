# 🏨 Room Management Module - Member 2 (Admin)

## 📋 Project Overview
This module implements the **Room Management System** for the HotelForge-3 project, providing complete CRUD (Create, Read, Update, Delete) functionality for hotel room administration.

---

## 👤 Team Member Information
- **Role:** Member 2 - Admin: Room Management
- **Responsibilities:** 
  1. Create Room Management Panel (CRUD)
  2. Add/Update/Delete room records
  3. Show rooms in JTable with search (RowFilter)
  4. Validate unique room numbers

---

## 📁 My Module Files

### **Core Files (Required)**

#### 1. **UI Layer**
- **File:** `src/com/hotel/ui/RoomManagementPanel.java`
  - **Purpose:** Main GUI panel for room management
  - **Features:**
    - Add, Update, Delete room operations
    - JTable display with search functionality
    - Form validation and error handling
    - Real-time search using RowFilter
  - **Lines of Code:** 283 lines

#### 2. **Model Layer**
- **File:** `src/com/hotel/models/Room.java`
  - **Purpose:** Room entity class (OOP - Encapsulation)
  - **Properties:** roomNo, roomType, status, floor, amenities, price
  - **Lines of Code:** 45 lines

- **File:** `src/com/hotel/models/RoomType.java`
  - **Purpose:** Enum for room types with polymorphic pricing
  - **Types:** SINGLE, DOUBLE, DELUXE, SUITE, PRESIDENTIAL
  - **Polymorphism:** Each type overrides `getBasePrice()` method
  - **Lines of Code:** 21 lines

- **File:** `src/com/hotel/models/Amenity.java`
  - **Purpose:** Enum for room amenities
  - **Values:** AC, WIFI, TV
  - **Lines of Code:** 6 lines

#### 3. **DAO Layer (Data Access Object)**
- **File:** `src/com/hotel/dao/RoomDAO.java`
  - **Purpose:** Data access layer for room operations
  - **Features:**
    - CRUD operations using ArrayList (before DB sync)
    - Unique room number validation
    - Exception handling for duplicate entries
  - **Lines of Code:** 34 lines

---

## 🎯 Core Java Concepts Applied

### 1. **Object-Oriented Programming (OOP)**
- ✅ **Encapsulation:** `Room` class with private fields and public getters/setters
- ✅ **Abstraction:** Separation of concerns (UI, Model, DAO layers)
- ✅ **Inheritance:** Enum classes extending base Enum functionality

### 2. **Polymorphism**
- ✅ Different room types override `getBasePrice()` method
- ✅ Dynamic pricing based on room type selection

### 3. **Collections Framework**
- ✅ `ArrayList<Room>` for storing room data before database synchronization
- ✅ `List<Amenity>` for managing room amenities
- ✅ Stream API for amenities display

### 4. **Exception Handling**
- ✅ `IllegalArgumentException` for duplicate room numbers
- ✅ `NumberFormatException` for invalid input validation
- ✅ Try-catch blocks in all CRUD operations

---

## 🛠️ Technologies Used

### **Java Swing Components**
- `JFrame` - Main window container
- `JPanel` - Layout organization
- `JTable` - Room data display
- `DefaultTableModel` - Table data management
- `JTextField` - Input fields
- `JComboBox` - Dropdown selections
- `JCheckBox` - Amenity selection
- `JButton` - Action buttons
- `TableRowSorter` & `RowFilter` - Search functionality

### **JDBC (Future Integration)**
- Currently using ArrayList for data storage
- Ready for database integration with `rooms` table
- DAO pattern implemented for easy JDBC migration

---

## 🚀 How to Run My Module

### **Standalone Execution**
```bash
# Navigate to project directory
cd "d:\Github 2025\HotelForge-3"

# Compile the Room Management module
javac -d bin src/com/hotel/models/*.java src/com/hotel/dao/*.java src/com/hotel/ui/RoomManagementPanel.java

# Run the Room Management Panel
java -cp bin com.hotel.ui.RoomManagementPanel
```

### **Integrated Execution**
The module can be integrated into the main application by calling:
```java
new RoomManagementPanel();
```

---

## ✨ Key Features Implemented

### 1. **CRUD Operations**
- ✅ **Create:** Add new rooms with validation
- ✅ **Read:** Display all rooms in JTable
- ✅ **Update:** Modify existing room details
- ✅ **Delete:** Remove rooms from system

### 2. **Search Functionality**
- ✅ Real-time search using `RowFilter`
- ✅ Case-insensitive regex matching
- ✅ Search by room number, type, or status

### 3. **Validation**
- ✅ Unique room number validation
- ✅ Number format validation (room no, price, floor)
- ✅ Duplicate entry prevention

### 4. **User Interface**
- ✅ Modern, professional design
- ✅ Color-coded buttons (Add=Green, Update=Blue, Delete=Red)
- ✅ Split-pane layout (Form | Table)
- ✅ Click-to-edit functionality

---

## 📊 Package Structure

```
src/
├── com/hotel/
│   ├── models/
│   │   ├── Room.java           ✅ MY FILE
│   │   ├── RoomType.java       ✅ MY FILE
│   │   └── Amenity.java        ✅ MY FILE
│   ├── dao/
│   │   └── RoomDAO.java        ✅ MY FILE
│   └── ui/
│       └── RoomManagementPanel.java  ✅ MY FILE
```

---

## 🔄 Integration with Team Members

### **Can We Reorganize Packages?**
**Answer: YES ✅**

### **Recommended Package Structure for Team Integration**

```
src/
├── com/hotel/
│   ├── models/              (Shared by all members)
│   │   ├── Room.java        (Member 2)
│   │   ├── RoomType.java    (Member 2)
│   │   ├── Amenity.java     (Member 2)
│   │   ├── Booking.java     (Member 3)
│   │   └── User.java        (Member 1)
│   │
│   ├── dao/                 (Data Access Layer)
│   │   ├── RoomDAO.java     (Member 2)
│   │   ├── BookingDAO.java  (Member 3)
│   │   └── UserDAO.java     (Member 1)
│   │
│   ├── ui/                  (User Interface Layer)
│   │   ├── RoomManagementPanel.java    (Member 2)
│   │   ├── BookingManagementPanel.java (Member 3)
│   │   └── LoginPanel.java             (Member 1)
│   │
│   ├── services/            (Business Logic)
│   │   ├── RoomService.java
│   │   ├── BookingService.java
│   │   └── AuthService.java
│   │
│   ├── database/            (Database Connection)
│   │   └── DatabaseConnection.java
│   │
│   └── main/                (Main Application)
│       └── HotelManagementApp.java  (Entry Point)
```

### **How to Integrate All Modules**

#### **Step 1: Create Main Application Class**
Create a main entry point that combines all modules:

**File:** `src/com/hotel/main/HotelManagementApp.java`
```java
package com.hotel.main;

import com.hotel.ui.*;
import javax.swing.*;
import java.awt.*;

public class HotelManagementApp extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("🏨 Hotel Management System");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1200, 700);
            
            JTabbedPane tabbedPane = new JTabbedPane();
            
            // Member 1: Login/User Management
            // tabbedPane.addTab("Login", new LoginPanel());
            
            // Member 2: Room Management (YOUR MODULE)
            tabbedPane.addTab("Room Management", new RoomManagementPanel());
            
            // Member 3: Booking Management
            // tabbedPane.addTab("Bookings", new BookingManagementPanel());
            
            mainFrame.add(tabbedPane);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}
```

#### **Step 2: Compile All Modules Together**
```bash
# Compile all Java files
javac -d bin src/com/hotel/**/*.java

# Run integrated application
java -cp bin com.hotel.main.HotelManagementApp
```

---

## 🗑️ Unnecessary Files to Remove

### **Files That Can Be Removed:**

1. **Duplicate Login Files:**
   - `Login/main/java/models/User.java` (duplicate of `src/com/hotel/models/User.java`)
   - `Login/main/java/ui/LoginForm.java` (should be in main src)
   - `Login/main/java/ui/RegistrationForm.java` (should be in main src)
   - `Login/main/java/utils/DBConnection.java` (duplicate database connection)

2. **Duplicate Main Files:**
   - `src/main/HotelManagementApp.java` (consolidate into one main)
   - `src/com/hotel/main/BookingManagementDemo.java` (demo file, not needed)
   - `src/com/hotel/main/SimpleHotelApp.java` (demo file, not needed)

3. **Duplicate Database Connections:**
   - `src/com/hotel/database/SimpleConnection.java`
   - Keep only: `src/com/hotel/database/UnifiedDatabaseConnection.java`

4. **Duplicate UI Files:**
   - `src/main/ui/AvailabilityGridDialog.java` (duplicate of `src/com/hotel/ui/AvailabilityGridDialog.java`)

### **Recommended Cleanup Commands:**
```bash
# Remove duplicate Login folder
rm -rf Login/

# Remove demo files
rm src/com/hotel/main/BookingManagementDemo.java
rm src/com/hotel/main/SimpleHotelApp.java

# Remove duplicate database connections
rm src/com/hotel/database/SimpleConnection.java

# Remove duplicate main folder
rm -rf src/main/
```

---

## 📝 Database Schema (For Future JDBC Integration)

### **Rooms Table Structure**
```sql
CREATE TABLE rooms (
    room_no INT PRIMARY KEY,
    room_type VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available',
    floor INT NOT NULL,
    amenities VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### **Sample Data**
```sql
INSERT INTO rooms (room_no, room_type, price, status, floor, amenities) VALUES
(101, 'SINGLE', 1500, 'Available', 1, 'AC, WIFI'),
(201, 'DOUBLE', 2500, 'Available', 2, 'AC, WIFI, TV'),
(301, 'DELUXE', 4000, 'Occupied', 3, 'AC, WIFI, TV');
```

---

## 🎨 Screenshots & Features

### **Main Features:**
1. **Add Room:** Fill form → Click "Add" → Room added to table
2. **Update Room:** Click table row → Modify fields → Click "Update"
3. **Delete Room:** Select row → Click "Delete" → Confirmation
4. **Search:** Type in search box → Table filters automatically
5. **Auto-Price:** Select room type → Price auto-fills

---

## 📌 Summary

### **Total Files in My Module: 5**
1. ✅ `RoomManagementPanel.java` (283 lines)
2. ✅ `Room.java` (45 lines)
3. ✅ `RoomType.java` (21 lines)
4. ✅ `Amenity.java` (6 lines)
5. ✅ `RoomDAO.java` (34 lines)

**Total Lines of Code:** ~389 lines

### **Integration Answer:**
**YES**, we can:
1. ✅ Remove unnecessary duplicate files
2. ✅ Convert packages for all teammates' portions
3. ✅ Add all files to a unified main package
4. ✅ Create a single entry point (`HotelManagementApp.java`)

### **Next Steps:**
1. Clean up duplicate files
2. Consolidate all modules into unified package structure
3. Create main application with tabbed interface
4. Integrate JDBC for database persistence
5. Add authentication layer (Member 1's work)
6. Integrate booking system (Member 3's work)

---

## 📞 Contact & Support
For questions about the Room Management module, contact Member 2.

---

**Last Updated:** 2025-11-22  
**Version:** 1.0  
**Status:** ✅ Complete & Ready for Integration
