package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.*;
import com.bsuir.util.Dialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientRoomsBookingController implements Initializable {
    private final String numberType;
    private int client_id, room_id, rserv_id;
    private Date date1, date2;
    private int daysCount;
    private final int price;
    private int servPrice = 0;
    public ListView<String> lvRooms;
    public ListView<String> lvServices;
    public DatePicker dpComeIn;
    public Spinner<Integer> spDays;
    public Button btBack;
    public Button btBook;
    public Label lbPrice;
    public Label lbDate;
    public Label lbNumber;


    ObservableList<Room> roomsList = FXCollections.observableArrayList();
    ObservableList<String> roomsNumberList = FXCollections.observableArrayList();
    ObservableList<Service> servicesList = FXCollections.observableArrayList();
    ObservableList<String> serviceNameList = FXCollections.observableArrayList();

    public ClientRoomsBookingController(String numberType, int price) {
        this.numberType = numberType;
        this.price = price;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Role r = new Role(Connect.id, Connect.role);
        Connect.client.sendMessage("clientInfo");
        Connect.client.sendObject(r);
        Clients clients = (Clients) Connect.client.readObject();
        client_id = clients.getClientId();

        date1 = Date.valueOf(LocalDate.now());

        dpComeIn.setValue(LocalDate.now());

        SpinnerValueFactory<Integer> spinnerVF = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,1);
        spDays.setValueFactory(spinnerVF);
        daysCount = spDays.getValue();

        date2 = Date.valueOf(LocalDate.now().plusDays(daysCount));
        lbDate.setText("с " + date1.toString() + " - по " + date2.toString());

        lbPrice.setText(Integer.toString((price + servPrice) * daysCount));

        lvRooms.setItems(getRoomsNumberList());
        lvRooms.getSelectionModel().selectFirst();
        room_id = roomsList.getFirst().getId();

        lvServices.setItems(getServiceNameList());
        lvServices.getSelectionModel().selectFirst();
        rserv_id = 0;

        lbNumber.setText("Номера типа: " + numberType);

        //изменения даты
        dpComeIn.valueProperty().addListener((observableValue, localDate, t1) -> {
            date1 = Date.valueOf(t1);
            date2 = Date.valueOf(t1.plusDays(daysCount));
            lbDate.setText("с " + date1.toString() + " - по " + date2.toString());
        });

        //изменения количества дней
        spDays.valueProperty().addListener((observableValue, integer, t1) -> {
            daysCount = spDays.getValue();
            lbPrice.setText(Integer.toString((price + servPrice) * daysCount));
            date2 = Date.valueOf(dpComeIn.getValue().plusDays(daysCount));
            lbDate.setText("с " + date1.toString() + " - по " + date2.toString());
        });

        //
        lvRooms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    for (Room item: roomsList) {
                        if (t1.equals("Номер " + item.getNumber())) {
                            room_id = item.getId();
                        }
                    }
                }
            }
        });

        lvServices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    for (Service item: servicesList) {
                        if (item.getName().equals(t1)) {
                            servPrice = item.getPrice();
                            rserv_id = item.getId();
                        }
                    }
                    lbPrice.setText(Integer.toString((price + servPrice) * daysCount));
                }
            }
        });

        //кнопка Забронировать
        btBook.setOnAction(actionEvent -> {
            //Проверка на текущую дату
            Date date_now = Date.valueOf(LocalDate.now());
            if (date1.before(date_now)) {
                Dialog.showAlertDate();
                return;
            }

            Booking b = new Booking();
            b.setCheck_in(date1);
            b.setCheck_out(date2);
            b.setClient_id(client_id);
            b.setRoom_id(room_id);
            b.setServ_id(rserv_id);

            //Проверка наличия номера на указанный период
            Connect.client.sendMessage("bookingCheckRoom");
            Connect.client.sendObject(b);
            String check = "";
            try {
                check = Connect.client.readMessage();
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
            if (check.equals("Заказ невозможен")) {
                Dialog.showAlertBooking();
                return;
            }

            //Процедура бронирования
            Connect.client.sendMessage("addBooking");
            Connect.client.sendObject(b);

            String mes = "";
            try {
                mes = Connect.client.readMessage();
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
            if (mes.equals("OK")) {
                Dialog.correctOperation();

                btBook.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/client-rooms-view.fxml"));

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
            }
        });

        //кнопка Назад
        btBack.setOnAction(actionEvent -> {
            btBack.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/client-rooms-view.fxml"));

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
    }

    private ObservableList<String> getRoomsNumberList() {
        Connect.client.sendMessage("roomsNumberList");
        Connect.client.sendObject(numberType);
        ArrayList<Room> rooms = (ArrayList<Room>) Connect.client.readObject();
        roomsList.addAll(rooms);

        ArrayList<String> roomsNumber = new ArrayList<>();
        for (Room item: rooms) {
            roomsNumber.add("Номер " + item.getNumber());
        }
        roomsNumberList.addAll(roomsNumber);

        return roomsNumberList;
    }

    private ObservableList<String> getServiceNameList() {
        Connect.client.sendMessage("serviceList");
        ArrayList<Service> services = (ArrayList<Service>) Connect.client.readObject();
        servicesList.addAll(services);

        ArrayList<String> servicesName = new ArrayList<>();
        for (Service item: services) {
            servicesName.add(item.getName());
        }
        serviceNameList.addAll(servicesName);

        return serviceNameList;
    }
}
