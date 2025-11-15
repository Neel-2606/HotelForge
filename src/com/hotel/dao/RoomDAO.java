package com.hotel.dao;

import com.hotel.models.*;
import java.util.*;

public class RoomDAO {
    private List<Room> roomList = new ArrayList<>();
    public void addRoom(Room r) {
        for (Room existing : roomList) {
            if (existing.getRoomNo() == r.getRoomNo()) {
                throw new IllegalArgumentException("Room number already exists!");
            }
        }
        roomList.add(r);
    }

    public List<Room> getAllRooms() {
        return roomList;
    }

    public boolean updateRoom(Room r) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomNo() == r.getRoomNo()) {
                roomList.set(i, r);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(int roomNo) {
        return roomList.removeIf(r -> r.getRoomNo() == roomNo);
    }
}