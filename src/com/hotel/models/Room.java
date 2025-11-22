package com.hotel.models;
import java.util.List;
import java.util.stream.Collectors;
public class Room 
{
    private int roomNo;        //Stores the room number
    private RoomType roomType; //Stores the room type:Single,Double...                                        
    private String status;     //Stores the room status:available,occupied...
    private int floor;         //Stores the room floor
    private List<Amenity> amenities; //Stores the room amenities:AC,WiFi...
    private double price;      //Stores the room price

    public Room(int roomNo, RoomType roomType, String status, int floor, List<Amenity> amenities, double price) 
    {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.status = status;
        this.floor = floor;
        this.amenities = amenities;
        this.price = price;
    }
    // Getter Methods:return the value.
    public int getRoomNo() 
    {                                   //Returns room number
        return roomNo; 
    } 
    public RoomType getRoomType() 
    {                                   //Returns room type
        return roomType; 
    }
    public String getStatus() 
    {                                   //Returns room status
        return status; 
    }
    public int getFloor() 
    {                                   //Returns room floor
        return floor; 
    }
    public List<Amenity> getAmenitiesList() 
    {                                   //Returns room amenities list
        return amenities; 
    }
    public double getPrice() 
    {                                   //Returns room price
        return price; 
    }
    public String getAmenities() 
    {                                   //Returns room amenities,Convert Amenities List into a String
        return amenities.stream().map(Enum::name).collect(Collectors.joining(", "));
    }
    // Setter Methods:change/update the value.
    public void setRoomType(RoomType roomType) 
    {                                   //Sets room type
        this.roomType = roomType; 
    }
    public void setStatus(String status) 
    {                                   //Sets room status
        this.status = status; 
    }   
    public void setFloor(int floor) 
    {                                   //Sets room floor
        this.floor = floor; 
    }
    public void setAmenities(List<Amenity> amenities) 
    {                                   //Sets room amenities
        this.amenities = amenities; 
    }
    public void setPrice(double price) 
    {                                   //Sets room price
        this.price = price; 
    }
    public String toString() 
    {                                   //Returns room details
        return "Room " + roomNo + " (" + roomType + ")";
    }
}