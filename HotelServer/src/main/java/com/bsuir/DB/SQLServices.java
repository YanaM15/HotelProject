package com.bsuir.DB;

import com.bsuir.hotelorg.Service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class SQLServices implements ISQLServices {

    private static SQLServices instance;
    private ConnectionDB dbConnection;

    private SQLServices() throws SQLException, ClassNotFoundException {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized SQLServices getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null)
            instance = new SQLServices();
        return instance;
    }

    @Override
    public ArrayList<Service> get() {
        String str = "select rserv_id, rserv_name, rserv_price" +
                " from tb_roomservices;";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Service> servicesList = new ArrayList<>();
        for (String[] items: result){
            Service service = new Service();
            service.setId(Integer.parseInt(items[0]));
            service.setName(items[1]);
            service.setPrice(Integer.parseInt(items[2]));

            servicesList.add(service);
        }
        return servicesList;
    }

    @Override
    public boolean deleteService(Service s) {
        return false;
    }

    @Override
    public boolean insertService(Service s) {
        String proc = "{call insert_service(?,?)}";

        try (CallableStatement callableStatement = ConnectionDB.dbConnection.prepareCall(proc)) {
            callableStatement.setString(1, s.getName());
            callableStatement.setInt(2, s.getPrice());

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
    public boolean changeService(Service s) {
        return false;
    }
}
