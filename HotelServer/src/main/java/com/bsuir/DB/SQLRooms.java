package com.bsuir.DB;

import com.bsuir.hotelorg.Room;
import com.bsuir.hotelorg.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public class SQLRooms implements ISQLRooms{
    private static SQLRooms instance;
    private ConnectionDB dbConnection;

    private SQLRooms() throws SQLException, ClassNotFoundException {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized SQLRooms getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null)
            instance = new SQLRooms();
        return instance;
    }

    @Override
    public ArrayList<Room> get() {
        String str = "select *" +
                " from tb_roomtype;";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        ArrayList<Room> roomsList = new ArrayList<>();
        for (String[] items: result){
            Room room = new Room();
            room.setId(Integer.parseInt(items[0]));
            room.setName(items[1]);
            room.setDescription(items[2]);
            room.setPrice(Integer.parseInt(items[3]));
            room.setSquare(Integer.parseInt(items[4]));
            room.setPlace(items[5]);
            room.setExplace(items[6]);

            roomsList.add(room);
        }
        return roomsList;
    }

    @Override
    public ArrayList<Room> getRooms(String type) {
        String str = String.format("select tb_rooms.*, tb_roomtype.rtype_name, tb_roomtype.rtype_price from tb_rooms" +
                " left join tb_roomtype on tb_rooms.rtype_id = tb_roomtype.rtype_id" +
                " where tb_roomtype.rtype_name = '%s';", type);
        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        ArrayList<Room> roomsList = new ArrayList<>();
        for (String[] items: result){
            Room room = new Room();
            room.setId(Integer.parseInt(items[0]));
            room.setNumber(Integer.parseInt(items[1]));
            room.setLevel(Integer.parseInt(items[2]));
            room.setName(items[4]);
            room.setPrice(Integer.parseInt(items[5]));

            roomsList.add(room);
        }
        return roomsList;
    }

    @Override
    public boolean changeRoom(Room r) {
        String str = String.format("update tb_roomtype " +
                "set rtype_discription = '%s', rtype_explace = '%s', " +
                "rtype_price = '%d' " +
                "where tb_roomtype.rtype_id = '%d';", r.getDescription(), r.getExplace(), r.getPrice(), r.getId());
        try {
            dbConnection.execute(str);
        } catch (Exception e) {
            System.out.println("ошибка изменения данных комнаты");
            return false;
        }
        return true;
    }
}
