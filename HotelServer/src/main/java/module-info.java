module com.bsuir.hotelserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bsuir.HotelServer to javafx.fxml;
    exports com.bsuir.HotelServer;
}