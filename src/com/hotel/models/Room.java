package com.hotel.models;

import java.util.List;
import java.util.stream.Collectors;

public class Room 
{
    private int roomNo;
    private RoomType roomType;
    private String status;
    private int floor;
    private List<Amenity> amenities;
    private double price;

    public Room(int roomNo, RoomType roomType, String status, int floor, List<Amenity> amenities, double price) 
    {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.status = status;
        this.floor = floor;
        this.amenities = amenities;
        this.price = price;
    }

    public int getRoomNo() 
    { 
        return roomNo; 
    }
    
    public RoomType getRoomType() 
    { 
        return roomType; 
    }
    
    public String getStatus() 
    { 
        return status; 
    }
    
    public int getFloor() 
    { 
        return floor; 
    }
    
    public List<Amenity> getAmenitiesList() 
    { 
        return amenities; 
    }
    
    public double getPrice() 
    { 
        return price; 
    }

    public String getAmenities() 
    {
        return amenities.stream().map(Enum::name).collect(Collectors.joining(", "));
    }

    public void setRoomType(RoomType roomType) 
    { 
        this.roomType = roomType; 
    }
    
    public void setStatus(String status) 
    { 
        this.status = status; 
    }
    
    public void setFloor(int floor) 
    { 
        this.floor = floor; 
    }
    
    public void setAmenities(List<Amenity> amenities) 
    { 
        this.amenities = amenities; 
    }
    
    public void setPrice(double price) 
    { 
        this.price = price; 
    }

    public String toString() 
    {
        return "Room " + roomNo + " (" + roomType + ")";
    }
}
