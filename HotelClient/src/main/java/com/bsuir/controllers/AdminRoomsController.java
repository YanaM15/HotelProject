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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminRoomsController implements Initializable {
    @FXML
    private Button btnChange;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfExtraPlaces;
    @FXML
    private TableView<Room> tvRooms;
    @FXML
    private TableColumn<Room, String> tcType;
    @FXML
    private TableColumn<Room, Integer> tcSquare;
    @FXML
    private TableColumn<Room, String> tcPlaces;
    @FXML
    private Button btnBack;

    ObservableList<Room> roomsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ChangeListener для таблицы
        tvRooms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room room, Room t1) {
                if (t1 != null) {
                    taDescription.setText(t1.getDescription());
                    tfExtraPlaces.setText(t1.getExplace());
                    tfPrice.setText(Integer.toString(t1.getPrice()));
                }
            }
        });

        //ChangeListener для поля Цены
        tfPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tfPrice.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Кнопка Изменить
        btnChange.setOnAction(actionEvent -> {
            if (checkInput())
                Dialog.showAlertWithNullInput();
            else {
                Room r = tvRooms.getSelectionModel().getSelectedItem();
                r.setPrice(Integer.parseInt(tfPrice.getText()));
                r.setExplace(tfExtraPlaces.getText());
                r.setDescription(taDescription.getText());

                Connect.client.sendMessage("changeRoom");
                Connect.client.sendObject(r);

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("Ошибка при изменении данных комнаты"))
                    Dialog.showAlertSQL();
                else {
                    Dialog.correctOperation();
                    tvRooms.setItems(getRoomsList());
                    tvRooms.getSelectionModel().selectFirst();
                }
            }
        });

        //Кнопка Назад
        btnBack.setOnAction(actionEvent -> {
            btnBack.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/menu-admin.fxml"));

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

        tcType.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        tcSquare.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getSquare()));
        tcPlaces.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPlace()));

        tvRooms.setItems(getRoomsList());
        tvRooms.getSelectionModel().selectFirst();
    }

    private ObservableList<Room> getRoomsList() {
        Connect.client.sendMessage("roomsList");
        ArrayList<Room> rooms = (ArrayList<Room>) Connect.client.readObject();
        System.out.println(rooms);
        roomsList.addAll(rooms);

        return roomsList;
    }

    private boolean checkInput() {
        try {
            return tfPrice.getText().isEmpty() || tfExtraPlaces.getText().isEmpty() ||
                    taDescription.getText().isEmpty();
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
