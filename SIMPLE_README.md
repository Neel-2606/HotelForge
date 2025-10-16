# Simple Hotel Management System - Member 4: Booking Management

## 🎯 **For 1st Year CSE Students**

This is a **simplified version** of the Hotel Management System that focuses on **basic programming concepts** that are easy to understand and explain.

## 📚 **Core Java Concepts Covered**

### 1. **Object-Oriented Programming (OOP)**
```java
// Encapsulation - private variables with public getters/setters
public class Booking {
    private int bookingId;        // Private variable
    private String customerName;  // Private variable
    
    public int getBookingId() {   // Public getter method
        return bookingId;
    }
    
    public void setBookingId(int bookingId) { // Public setter method
        this.bookingId = bookingId;
    }
}
```

### 2. **Collections (ArrayList)**
```java
// Using ArrayList to store multiple bookings
ArrayList<Booking> bookingList = new ArrayList<>();

// Adding items
bookingList.add(booking);

// Getting size
int count = bookingList.size();

// Accessing items
Booking firstBooking = bookingList.get(0);
```

### 3. **Exception Handling (Try-Catch)**
```java
try {
    // Database operation that might fail
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
} catch (SQLException e) {
    // Handle the error
    System.out.println("Error: " + e.getMessage());
}
```

### 4. **JDBC (Database Operations)**
```java
// Simple database connection
Connection connection = DriverManager.getConnection(url, username, password);

// Simple query
String query = "SELECT * FROM bookings";
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(query);
```

### 5. **Swing GUI Components**
```java
// Basic Swing components
JFrame frame = new JFrame("Hotel Management");
JButton button = new JButton("Check In");
JTable table = new JTable();
JTextField textField = new JTextField();
```

## 📁 **Simplified Project Structure**

```
src/com/hotel/
├── models/
│   └── Booking.java                  # Simple class with getters/setters
├── managers/
│   └── SimpleBookingManager.java     # Basic database operations
├── ui/
│   └── SimpleBookingPanel.java       # Basic Swing GUI
├── database/
│   └── SimpleConnection.java         # Simple database connection
└── main/
    └── SimpleHotelApp.java           # Main class to run the app

database/
└── simple_schema.sql                 # Simple database with sample data
```

## 🚀 **How to Run the Project**

### Step 1: Setup Database
1. Install MySQL on your computer
2. Open MySQL Command Line or MySQL Workbench
3. Run the SQL file:
```sql
source database/simple_schema.sql
```

### Step 2: Update Database Settings
1. Open `SimpleConnection.java`
2. Change the password to your MySQL password:
```java
private static final String PASSWORD = "your_mysql_password";
```

### Step 3: Run the Application
1. Compile the Java files
2. Run the main class:
```bash
java com.hotel.main.SimpleHotelApp
```

## 🖥️ **What the Application Does**

### Main Features:
1. **View All Bookings** - Shows all hotel bookings in a table
2. **Check-In** - Changes booking status from CONFIRMED to CHECKED_IN
3. **Check-Out** - Changes booking status from CHECKED_IN to CHECKED_OUT
4. **Cancel Booking** - Changes booking status to CANCELLED
5. **Search** - Find bookings by customer name

### GUI Components:
- **JTable** - Displays booking data
- **JButton** - For actions (Check-In, Check-Out, etc.)
- **JTextField** - For search input
- **JOptionPane** - For showing messages

## 📝 **Key Code Explanations for Teacher**

### 1. **Booking Class (OOP Concepts)**
```java
public class Booking {
    // Private variables (Encapsulation)
    private int bookingId;
    private String customerName;
    
    // Constructor
    public Booking() {
        this.status = "CONFIRMED";
    }
    
    // Getter method
    public String getCustomerName() {
        return customerName;
    }
    
    // Setter method
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
```
**Explanation**: This shows encapsulation (private variables) and how we access them through public methods.

### 2. **ArrayList Usage (Collections)**
```java
ArrayList<Booking> bookingList = new ArrayList<>();

// Adding objects
bookingList.add(new Booking());

// Getting size
int totalBookings = bookingList.size();

// Looping through ArrayList
for (int i = 0; i < bookingList.size(); i++) {
    Booking booking = bookingList.get(i);
    System.out.println(booking.getCustomerName());
}
```
**Explanation**: ArrayList is a dynamic array that can grow/shrink. We use it to store multiple Booking objects.

### 3. **Database Operations (JDBC)**
```java
// Connect to database
Connection connection = DriverManager.getConnection(url, username, password);

// Execute query
String query = "SELECT * FROM bookings";
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(query);

// Read results
while (rs.next()) {
    String name = rs.getString("customer_name");
    int roomId = rs.getInt("room_id");
}
```
**Explanation**: JDBC allows Java to connect to databases. We use SQL queries to get/update data.

### 4. **Event Handling (Swing)**
```java
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // This code runs when button is clicked
        System.out.println("Button clicked!");
    }
});
```
**Explanation**: ActionListener is an interface that handles button clicks and other user actions.

## 🎓 **Learning Outcomes**

After studying this code, students will understand:

1. **OOP Basics**: Classes, objects, encapsulation, constructors
2. **Collections**: How to use ArrayList to store multiple objects
3. **Exception Handling**: Try-catch blocks for error handling
4. **Database Connectivity**: Basic JDBC operations
5. **GUI Programming**: Basic Swing components and event handling
6. **Program Structure**: How to organize code into packages and classes

## 📊 **Database Tables**

### Bookings Table:
| Column | Type | Description |
|--------|------|-------------|
| booking_id | INT | Unique booking number |
| room_id | INT | Room number |
| customer_name | VARCHAR | Customer's name |
| customer_email | VARCHAR | Customer's email |
| customer_phone | VARCHAR | Customer's phone |
| check_in_date | DATE | Check-in date |
| check_out_date | DATE | Check-out date |
| status | VARCHAR | CONFIRMED/CHECKED_IN/CHECKED_OUT/CANCELLED |
| total_amount | DECIMAL | Total booking amount |

## 🔧 **Common Issues and Solutions**

### Issue 1: Database Connection Failed
**Solution**: 
- Make sure MySQL is running
- Check username/password in `SimpleConnection.java`
- Ensure database `hotel_management` exists

### Issue 2: Table Not Found
**Solution**: 
- Run the `simple_schema.sql` file first
- Check if tables were created: `SHOW TABLES;`

### Issue 3: Driver Not Found
**Solution**: 
- Download MySQL Connector/J
- Add the JAR file to your classpath

## 👨‍🏫 **For Teachers**

This simplified version focuses on:
- **Basic OOP concepts** that 1st year students can understand
- **Simple ArrayList operations** instead of complex Collections
- **Basic exception handling** with try-catch
- **Straightforward JDBC** operations
- **Simple Swing GUI** with common components

The code is well-commented and uses simple logic that students can follow and explain easily during presentations.

## 🏆 **Team Member 4 Contribution**

As Member 4, this module demonstrates:
- ✅ **Booking Management** functionality
- ✅ **Check-In/Check-Out** operations  
- ✅ **Basic Collections** (ArrayList)
- ✅ **Exception Handling** (try-catch)
- ✅ **JDBC Operations** (database connectivity)
- ✅ **Swing GUI** (user interface)
- ✅ **OOP Concepts** (classes, objects, encapsulation)

This provides a solid foundation for understanding hotel management systems while keeping the complexity appropriate for 1st year CSE students.
