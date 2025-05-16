package com.bsuir.DB;

import com.bsuir.hotelorg.Booking;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class SQLBooking implements ISQLBooking{

    private static SQLBooking instance;
    private ConnectionDB dbConnection;


    private SQLBooking() throws SQLException, ClassNotFoundException {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized SQLBooking getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null)
            instance = new SQLBooking();
        return instance;
    }

    @Override
    public ArrayList<Booking> get() {
        String str = "select tb_booking.*, tb_clients.client_name, tb_clients.client_surname, tb_rooms.room_number," +
                " tb_rooms.room_level, tb_roomservices.rserv_name," +
                " calc_cost(tb_booking.room_id, tb_booking.serv_id, tb_booking.check_in, tb_booking.check_out) as cost from tb_booking" +
                " left join tb_clients on tb_booking.client_id = tb_clients.client_id" +
                " left join tb_rooms on tb_booking.room_id = tb_rooms.room_id" +
                " left join tb_roomservices on tb_booking.serv_id = tb_roomservices.rserv_id;";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        ArrayList<Booking> bookingList = new ArrayList<>();
        for (String[] items: result){
            Booking booking = new Booking();
            booking.setId(Integer.parseInt(items[0]));
            booking.setClient_id(Integer.parseInt(items[1]));
            booking.setRoom_id(Integer.parseInt(items[2]));
            booking.setServ_id(Integer.parseInt(items[3]));
            booking.setCheck_in_str(items[4]);
            booking.setCheck_out_str(items[5]);
            booking.setStatus(Integer.parseInt(items[6]));
            booking.setClient_name(items[7]);
            booking.setClient_surname(items[8]);
            booking.setRoom_number(Integer.parseInt(items[9]));
            booking.setRoom_level(Integer.parseInt(items[10]));
            booking.setServ_name(items[11]);
            booking.setCost(Integer.parseInt(items[12]));

            bookingList.add(booking);
        }
        return bookingList;
    }

    @Override
    public ArrayList<Booking> get(int client_id) {
        String str = "select tb_booking.*, tb_clients.client_name, tb_clients.client_surname, tb_rooms.room_number," +
                " tb_rooms.room_level, tb_roomservices.rserv_name, tb_roomtype.rtype_name," +
                " calc_cost(tb_booking.room_id, tb_booking.serv_id, tb_booking.check_in, tb_booking.check_out) as cost from tb_booking" +
                " left join tb_clients on tb_booking.client_id = tb_clients.client_id" +
                " left join tb_rooms on tb_booking.room_id = tb_rooms.room_id" +
                " left join tb_roomservices on tb_booking.serv_id = tb_roomservices.rserv_id" +
                " left join tb_roomtype on tb_rooms.rtype_id = tb_roomtype.rtype_id" +
                " where tb_booking.client_id = " + client_id + ";";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        ArrayList<Booking> bookingList = new ArrayList<>();
        for (String[] items: result){
            Booking booking = new Booking();
            booking.setId(Integer.parseInt(items[0]));
            booking.setClient_id(Integer.parseInt(items[1]));
            booking.setRoom_id(Integer.parseInt(items[2]));
            booking.setServ_id(Integer.parseInt(items[3]));
            booking.setCheck_in_str(items[4]);
            booking.setCheck_out_str(items[5]);
            booking.setStatus(Integer.parseInt(items[6]));
            booking.setClient_name(items[7]);
            booking.setClient_surname(items[8]);
            booking.setRoom_number(Integer.parseInt(items[9]));
            booking.setRoom_level(Integer.parseInt(items[10]));
            booking.setServ_name(items[11]);
            booking.setRoom_type(items[12]);
            booking.setCost(Integer.parseInt(items[13]));

            bookingList.add(booking);
        }
        return bookingList;
    }

    @Override
    public boolean deleteBooking(int booking_id) {
        String str = String.format("delete from db_hotel.tb_booking where booking_id = '%d';", booking_id);
        dbConnection.execute(str);
        return true;
    }

    @Override
    public boolean insertBooking(Booking b) {
        String proc = "{call insert_booking(?,?,?,?,?)}";

        try (CallableStatement callableStatement = ConnectionDB.dbConnection.prepareCall(proc)) {
            callableStatement.setInt(1, b.getClient_id());
            callableStatement.setInt(2, b.getRoom_id());
            callableStatement.setInt(3, b.getServ_id());
            callableStatement.setString(4, b.getCheck_in_str());
            callableStatement.setString(5, b.getCheck_out_str());

            callableStatement.execute();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ошибка");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean changeBooking(int booking_id) {
        String str = String.format("update `tb_booking` set `booking_status` = 2 where booking_id = '%d';", booking_id);
        dbConnection.execute(str);
        return true;
    }

    @Override
    public boolean checkBooking(Booking b) {
        String str = String.format("SELECT * FROM db_hotel.tb_booking " +
                "where room_id = '%d' and ('%s' between check_in and check_out) or " +
                        "('%s' between check_in and check_out) or (check_in between '%s' and '%s');",
                b.getRoom_id(), b.getCheck_in_str(), b.getCheck_out_str(), b.getCheck_in_str(), b.getCheck_out_str());
        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result.isEmpty();
    }
}
