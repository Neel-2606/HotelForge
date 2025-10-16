# Hotel Management Application

A comprehensive Java Swing-based hotel room management system that allows hotel staff to efficiently manage room inventory, bookings, and amenities.

## Features

- **Room Management**: Add, update, delete, and view hotel rooms
- **Room Types**: Support for Single, Double, Deluxe, Suite, and Presidential rooms
- **Amenities Tracking**: Track room amenities (AC, WiFi, TV)
- **Status Management**: Monitor room status (Available, Occupied, Maintenance, Cleaning, Out of Order)
- **Search & Filter**: Real-time search functionality to find rooms quickly
- **Modern UI**: Clean and intuitive Swing-based user interface

## Project Structure

```
src/
├── main/
│   ├── Main.java                    # Application entry point
│   ├── model/
│   │   ├── Room.java               # Room entity class
│   │   ├── RoomType.java           # Room type enumeration
│   │   └── Amenity.java            # Amenity enumeration
│   ├── dao/
│   │   └── RoomDAO.java            # Data access object for room operations
│   └── ui/
│       └── RoomManagementPanel.java # Main UI panel
```

## How to Run

1. **Compile the project:**
   ```bash
   javac -cp . -d bin src/main/model/*.java src/main/dao/*.java src/main/ui/*.java src/main/*.java
   ```

2. **Run the application:**
   ```bash
   java -cp bin main.Main
   ```

## Requirements

- Java 8 or higher
- Swing GUI library (included in standard JDK)

## Usage

1. **Adding a Room**: Fill in the room details and click "Add"
2. **Updating a Room**: Select a room from the table, modify details, and click "Update"
3. **Deleting a Room**: Select a room and click "Delete"
4. **Searching**: Use the search bar to filter rooms by any attribute
5. **Auto-pricing**: Room prices are automatically set based on room type

## Room Types & Base Prices

- Single: ₹1,500
- Double: ₹2,500
- Deluxe: ₹4,000
- Suite: ₹7,000
- Presidential: ₹12,000

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available under the MIT License.
