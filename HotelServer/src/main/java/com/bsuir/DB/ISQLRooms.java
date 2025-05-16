package com.bsuir.DB;

import com.bsuir.hotelorg.Room;

import java.util.ArrayList;

public interface ISQLRooms {
    ArrayList<Room> get();
    ArrayList<Room> getRooms(String type);
    boolean changeRoom(Room r);
}
