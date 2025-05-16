package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Booking;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminBookingsController implements Initializable {

    @FXML
    private TableView<Booking> tvBooking;
    @FXML
    private TableColumn<Booking, Integer> tcNumber;
    @FXML
    private TableColumn<Booking, String> tcClient;
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
    @FXML
    private TextField tfNumber;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnFindNumber;
    @FXML
    private Button btnFindDate;

    ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    ObservableList<Booking> bookingListFilter = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnFindNumber.setOnAction(actionEvent -> {
            if (checkInputNumber()) {
                tvBooking.getItems().clear();
                tvBooking.setItems(getBookingList());
                Dialog.showAlertWithNullInput();
            }
            else {
                String num = tfNumber.getText();
                for (Booking items: bookingList) {
                    if (Objects.equals(items.getRoom_number_str(), num))
                        bookingListFilter.add(items);
                }
                if (!bookingListFilter.isEmpty()) {
                    tvBooking.getItems().clear();
                    tvBooking.setItems(bookingListFilter);
                    tfNumber.clear();
                } else {
                    tfNumber.clear();
                    Dialog.incorrectSearch();
                }


            }
        });

        btnFindDate.setOnAction(actionEvent -> {
            if (dpDate.getValue() == null) {
                tvBooking.getItems().clear();
                tvBooking.setItems(getBookingList());
                Dialog.showAlertWithNullInput();
            } else {
                Date d = Date.valueOf(dpDate.getValue());
                for (Booking items: bookingList) {
                    if (items.getCheck_in_str().equals(d.toString()))
                        bookingListFilter.add(items);
                }
                if (!bookingListFilter.isEmpty()) {
                    tvBooking.getItems().clear();
                    tvBooking.setItems(bookingListFilter);
                    dpDate.setValue(null);
                } else {
                    dpDate.setValue(null);
                    Dialog.incorrectSearch();
                }
            }
        });

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

        tcNumber.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getRoom_number()));
        tcClient.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getClient_fullname()));
        tcCheckIn.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCheck_in_str()));
        tcCheckOut.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCheck_out_str()));
        tcServices.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getServ_name()));
        tcCost.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getCost()));
        tcStatus.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getStatus_str()));

        tvBooking.setItems(getBookingList());
    }

    private ObservableList<Booking> getBookingList() {
        Connect.client.sendMessage("bookingList");
        ArrayList<Booking> bookings = (ArrayList<Booking>) Connect.client.readObject();
        System.out.println(bookings);
        bookingList.addAll(bookings);

        return bookingList;
    }

    private boolean checkInputNumber() {
        try {
            return tfNumber.getText().isEmpty();
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
