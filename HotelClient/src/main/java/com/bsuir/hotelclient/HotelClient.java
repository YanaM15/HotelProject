package com.bsuir.hotelclient;

import com.bsuir.client.Connect;
import com.bsuir.client.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Date;


public class HotelClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HotelClient.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hotel Paradise");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Connect.client.sendMessage("exit");
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        Connect.client = new Client("127.0.0.1", "2424");
        launch();
    }
}