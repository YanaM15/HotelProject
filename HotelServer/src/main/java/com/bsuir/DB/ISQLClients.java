package com.bsuir.DB;

import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import com.bsuir.hotelorg.Status;

import java.util.ArrayList;

public interface ISQLClients {
    ArrayList<Clients> findClient(Clients obj);
    Role insert(Clients obj);
    boolean deleteClient(Clients obj);
    ArrayList<Clients> get();
    Clients getClient(Role r);
    boolean changeClient(Clients cl);
    boolean setStatus(Status s);
}
