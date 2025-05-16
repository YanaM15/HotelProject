package com.bsuir.DB;

import java.sql.SQLException;

public class SQLFactory extends AbstractFactory {
//    public SQLTeacher getTeachers() throws SQLException, ClassNotFoundException {
//        return SQLTeacher.getInstance();
//    }

    public SQLClients getClients() throws SQLException, ClassNotFoundException {
        return SQLClients.getInstance();
    }

    public SQLAuthorization getRole() throws SQLException, ClassNotFoundException {
        return SQLAuthorization.getInstance();
    }

    public SQLRooms getRooms() throws SQLException, ClassNotFoundException {
        return SQLRooms.getInstance();
    }

    public SQLBooking getBooking() throws SQLException, ClassNotFoundException {
        return SQLBooking.getInstance();
    }

    public SQLServices getServices() throws SQLException, ClassNotFoundException {
        return SQLServices.getInstance();
    }

    public SQLReview getReview() throws SQLException, ClassNotFoundException {
        return SQLReview.getInstance();
    }
}
