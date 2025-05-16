module com.bsuir.hotelclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bsuir.hotelclient to javafx.fxml;
    exports com.bsuir.hotelclient;
    exports com.bsuir.controllers;
    opens com.bsuir.controllers to javafx.fxml;
}