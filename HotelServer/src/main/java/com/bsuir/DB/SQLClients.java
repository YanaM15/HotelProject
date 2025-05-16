package com.bsuir.DB;

import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import com.bsuir.hotelorg.Status;

import java.sql.*;
import java.util.ArrayList;

public class SQLClients implements ISQLClients {
    private static SQLClients instance;
    private ConnectionDB dbConnection;

    private SQLClients() throws SQLException, ClassNotFoundException {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized SQLClients getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null)
            instance = new SQLClients();
        return instance;
    }
    @Override
    public ArrayList<Clients> findClient(Clients obj) {
        String str = "select `tb_keys`.login, client_name, client_surname, client_email, client_status" +
                " from tb_clients" +
                " join `tb_keys` on `tb_keys`.`keys_id` = tb_clients.keys_id" +
                " where `tb_keys`.login = \"" + obj.getLogin() + "\";";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Clients> studList = new ArrayList<>();
        for (String[] items: result){
            Clients clients = new Clients();
            clients.setLogin(items[0]);
            clients.setFirstname(items[1]);
            clients.setLastname(items[2]);
            clients.setEmail(items[3]);
            try {
                clients.setStatus(Integer.parseInt(items[4]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            studList.add(clients);
        }
        return studList;
    }

    @Override
    public Role insert(Clients obj) {
        String proc = "{call insert_client(?,?,?,?,?,?)}";
        Role r = new Role();
        try (CallableStatement callableStatement = ConnectionDB.dbConnection.prepareCall(proc)) {
            callableStatement.setString(1, obj.getFirstname());
            callableStatement.setString(2, obj.getLastname());
            callableStatement.setString(3, obj.getEmail());
            callableStatement.setString(4, obj.getLogin());
            callableStatement.setString(5, obj.getPassword());
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.execute();
            r.setId(callableStatement.getInt(6));
            r.setRole("client");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ошибка");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public boolean deleteClient(Clients obj) {
        return false;
    }

    @Override
    public ArrayList<Clients> get() {
        String str = "select `tb_keys`.login, client_name, client_surname, client_email, client_status, `tb_keys`.password" +
                " from tb_clients" +
                " join `tb_keys` on `tb_keys`.`keys_id` = tb_clients.keys_id";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Clients> clientsList = new ArrayList<>();
        for (String[] items: result){
            Clients clients = new Clients();
            clients.setLogin(items[0]);
            clients.setFirstname(items[1]);
            clients.setLastname(items[2]);
            clients.setEmail(items[3]);
            try {
                clients.setStatus(Integer.parseInt(items[4]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            clients.setPassword(items[5]);
            clientsList.add(clients);
        }
        return clientsList;
    }

    @Override
    public Clients getClient(Role r) {
        String str = "select `tb_keys`.login, client_name, client_surname, client_email, client_status, client_id" +
                ", `tb_keys`.password from tb_clients" +
                " join `tb_keys` on `tb_keys`.`keys_id` = tb_clients.keys_id" +
                " where tb_clients.keys_id = " + r.getId();
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        Clients clients = new Clients();
        for (String[] items: result){
            clients.setLogin(items[0]);
            clients.setFirstname(items[1]);
            clients.setLastname(items[2]);
            clients.setEmail(items[3]);
            clients.setStatus(Integer.parseInt(items[4]));
            clients.setClientId(Integer.parseInt(items[5]));
            clients.setPassword(items[6]);
        }
        return clients;
    }

    @Override
    public boolean changeClient(Clients cl) {
        String str = String.format("update tb_clients " +
                "join tb_keys on tb_clients.keys_id = tb_keys.keys_id " +
                "set tb_clients.client_name = '%s', tb_clients.client_surname = '%s', " +
                "tb_clients.client_email = '%s', tb_keys.password = '%s' " +
                "where tb_clients.client_id = '%d';", cl.getFirstname(), cl.getLastname(), cl.getEmail(), cl.getPassword(), cl.getClientId());
        try {
            dbConnection.execute(str);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("ошибка изменения данных клиента");
            return false;
        }
        return true;
    }

    @Override
    public boolean setStatus(Status s) {
        String proc = "{call set_status(?,?)}";
        try (CallableStatement callableStatement = ConnectionDB.dbConnection.prepareCall(proc)) {
            callableStatement.setString(1, s.getLogin());
            callableStatement.setInt(2, s.getBlock());
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("ошибка изменения статуса клиента");
            return false;
        }
        return true;
    }
}
