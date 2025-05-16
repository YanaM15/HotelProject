package com.bsuir.DB;

import com.bsuir.hotelorg.Service;

import java.util.ArrayList;

public interface ISQLServices {
    ArrayList<Service> get();
    boolean deleteService(Service s);
    boolean insertService(Service s);
    boolean changeService(Service s);
}
