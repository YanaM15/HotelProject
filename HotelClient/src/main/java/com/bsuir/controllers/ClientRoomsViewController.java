package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Room;
import com.bsuir.util.Dialog;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientRoomsViewController implements Initializable {
    @FXML
    private TableView<Room> tvRooms;
    @FXML
    private TableColumn<Room, String> tcName;
    @FXML
    private TableColumn<Room, Integer> tcSquare;
    @FXML
    private TableColumn<Room, String> tcPlaces;
    @FXML
    private TableColumn<Room, Integer> tcPrice;
    @FXML
    private ImageView imRoom;
    @FXML
    private TextArea taDiscription;
    @FXML
    private Label lbExtraPlaces;
    @FXML
    private Button btnBooking;
    @FXML
    private Button btnBack;

    ObservableList<Room> roomsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tvRooms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room room, Room t1) {
                if (t1 != null) {
                    taDiscription.setText(t1.getDescription());
                    lbExtraPlaces.setText(t1.getExplace());
                    switch (t1.getName()) {
                        case "одноместный" -> {
                            Image image = new Image(getClass().getResourceAsStream("/com/bsuir/hotelclient/images/одноместный.jpg"));
                            imRoom.setImage(image);
                        }
                        case "двухместный" -> {
                            Image image = new Image(getClass().getResourceAsStream("/com/bsuir/hotelclient/images/двухместный.jpg"));
                            imRoom.setImage(image);
                        }
                        case "дабл" -> {
                            Image image = new Image(getClass().getResourceAsStream("/com/bsuir/hotelclient/images/дабл.jpg"));
                            imRoom.setImage(image);
                        }
                        case "люкс" -> {
                            Image image = new Image(getClass().getResourceAsStream("/com/bsuir/hotelclient/images/люкс.jpg"));
                            imRoom.setImage(image);
                        }
                        case "семейный" -> {
                            Image image = new Image(getClass().getResourceAsStream("/com/bsuir/hotelclient/images/семейный.jpg"));
                            imRoom.setImage(image);
                        }
                        case "апартамент" -> {
                            Image image = new Image(getClass().getResourceAsStream("/com/bsuir/hotelclient/images/аппартмент.jpg"));
                            imRoom.setImage(image);
                        }
                    }
                }
            }
        });

        btnBooking.setOnAction(actionEvent -> {
            if (tvRooms.getSelectionModel().isEmpty()) {
                Dialog.roomNotSelected();
                return;
            }

            btnBooking.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            //Передача данных в окно бронирования
            String number = tcName.getCellData(tvRooms.getSelectionModel().getSelectedItem());
            int price = tcPrice.getCellData(tvRooms.getSelectionModel().getSelectedItem());
            ClientRoomsBookingController crbc = new ClientRoomsBookingController(number, price);
            loader.setController(crbc);

            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/client-rooms-booking.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene((root)));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Connect.client.sendMessage("exit");
                }
            });
            stage.show();
        });

        btnBack.setOnAction(actionEvent -> {
            btnBack.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/menu-client.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene((root)));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Connect.client.sendMessage("exit");
                }
            });
            stage.show();
        });

        tcName.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        tcSquare.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getSquare()));
        tcPlaces.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPlace()));
        tcPrice.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPrice()));

        tvRooms.setItems(getRoomsList());
    }

    private ObservableList<Room> getRoomsList() {
        Connect.client.sendMessage("roomsList");
        ArrayList<Room> rooms = (ArrayList<Room>) Connect.client.readObject();
        System.out.println(rooms);
        roomsList.addAll(rooms);

        return roomsList;
    }
}
