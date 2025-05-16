package com.bsuir.DB;

import java.sql.SQLException;

public abstract class AbstractFactory {
    public abstract SQLClients getClients() throws SQLException, ClassNotFoundException;
    public abstract SQLAuthorization getRole() throws SQLException, ClassNotFoundException;
    public abstract SQLRooms getRooms() throws SQLException, ClassNotFoundException;
    public abstract SQLBooking getBooking() throws SQLException, ClassNotFoundException;
    public abstract SQLServices getServices() throws SQLException, ClassNotFoundException;
    public abstract SQLReview getReview() throws SQLException, ClassNotFoundException;
}
