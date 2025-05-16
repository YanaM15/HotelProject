package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Booking;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import com.bsuir.util.Dialog;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientBookingController implements Initializable {
    @FXML
    private Button btnBay;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<Booking> tvBooking;
    @FXML
    private TableColumn<Booking, Integer> tcNumber;
    @FXML
    private TableColumn<Booking, String> tcType;
    @FXML
    private TableColumn<Booking, String> tcCheckIn;
    @FXML
    private TableColumn<Booking, String> tcCheckOut;
    @FXML
    private TableColumn<Booking, String> tcServices;
    @FXML
    private TableColumn<Booking, Integer> tcCost;
    @FXML
    private TableColumn<Booking, String> tcStatus;
    @FXML
    private Button btnBack;

    ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Role r = new Role(Connect.id, Connect.role);
        Connect.client.sendMessage("clientInfo");
        Connect.client.sendObject(r);
        Clients clients = (Clients) Connect.client.readObject();

        // Кнопка Оплатить бронь
        btnBay.setOnAction(actionEvent -> {
            if (tvBooking.getItems().isEmpty())
                return;
            Booking b = tvBooking.getSelectionModel().getSelectedItem();
            if (b == null)
                return;
            if (b.getStatus() == 2)
                Dialog.bookingPaid();
            else {
                Connect.client.sendMessage("bookingPaid");
                Connect.client.sendObject(b.getId());

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("Ошибка при оплате заказа"))
                    Dialog.showAlertWithNoLogin();

                tvBooking.getItems().clear();
                tvBooking.setItems(getBookingList(clients.getClientId()));
                //tvBooking.getSelectionModel().selectFirst();
            }
        });

        //Кнопка Отменить бронь
        btnCancel.setOnAction(actionEvent -> {
            if (tvBooking.getItems().isEmpty())
                return;
            Booking b = tvBooking.getSelectionModel().getSelectedItem();
            if (b == null)
                return;
            if (b.getStatus() == 2)
                Dialog.bookingPaid();
            else {
                Connect.client.sendMessage("bookingCancel");
                Connect.client.sendObject(b.getId());

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("Ошибка при отмене заказа"))
                    Dialog.showAlertWithNoLogin();

                tvBooking.getItems().clear();
                tvBooking.setItems(getBookingList(clients.getClientId()));
                //tvBooking.getSelectionModel().selectFirst();
            }
        });

        //Кнопка Назад
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

        //Инициализация столбцов таблицы
        tcNumber.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getRoom_number()));
        tcType.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getRoom_type()));
        tcCheckIn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCheck_in_str()));
        tcCheckOut.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCheck_out_str()));
        tcServices.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getServ_name()));
        tcCost.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCost()));
        tcStatus.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getStatus_str()));

        tvBooking.setItems(getBookingList(clients.getClientId()));
        //tvBooking.getSelectionModel().selectFirst();
    }

    private ObservableList<Booking> getBookingList(int client_id) {
        Connect.client.sendMessage("bookingListClient");
        Connect.client.sendObject(client_id);
        ArrayList<Booking> bookings = (ArrayList<Booking>) Connect.client.readObject();
        System.out.println(bookings);
        bookingList.addAll(bookings);

        return bookingList;
    }
}