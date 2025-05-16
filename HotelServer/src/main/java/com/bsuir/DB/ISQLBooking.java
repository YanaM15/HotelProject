package com.bsuir.DB;

import com.bsuir.hotelorg.Booking;
import com.bsuir.hotelorg.Service;

import java.util.ArrayList;

public interface ISQLBooking {
    ArrayList<Booking> get();
    ArrayList<Booking> get(int client_id);
    boolean deleteBooking(int booking_id);
    boolean insertBooking(Booking b);
    boolean changeBooking(int booking_id);
    boolean checkBooking(Booking b);
}
