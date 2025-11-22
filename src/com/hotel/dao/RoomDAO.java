package com.hotel.dao;

import com.hotel.models.Amenity;
import com.hotel.models.Room;
import com.hotel.models.RoomType;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO 
{
    private List<Room> roomList;
    
    public RoomDAO()
    {
        roomList = new ArrayList<Room>();
    }
    
    public void addRoom(Room r) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            Room existing = roomList.get(i);
            if (existing.getRoomNo() == r.getRoomNo()) 
            {
                throw new IllegalArgumentException("Room number already exists!");
            }
        }
        roomList.add(r);
    }

    public List<Room> getAllRooms() 
    {
        return roomList;
    }

    public boolean updateRoom(Room r) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            if (roomList.get(i).getRoomNo() == r.getRoomNo()) 
            {
                roomList.set(i, r);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(int roomNo) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            if (roomList.get(i).getRoomNo() == roomNo) 
            {
                roomList.remove(i);
                return true;
            }
        }
        return false;
    }
}