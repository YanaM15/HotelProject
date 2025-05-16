package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MenuClientController {
    @FXML
    private Label lbLogin;
    @FXML
    private Label lbName;
    @FXML
    private Label lbBlock;
    @FXML
    private Button btnViewRooms;
    @FXML
    private Button btnClientAccount;
    @FXML
    private Button btnBooking;
    @FXML
    private Button btnReview;
    @FXML
    private Button btnBack;

    @FXML
    void initialize(){
        System.out.println("Connect info:");
        System.out.println(Connect.id);
        System.out.println(Connect.role);

        Role r = new Role(Connect.id, Connect.role);
        Connect.client.sendMessage("clientInfo");
        Connect.client.sendObject(r);

        Clients clients = (Clients) Connect.client.readObject();

        lbLogin.setText(clients.getLogin());
        lbName.setText(clients.getFirstname() + " " + clients.getLastname());

        if (clients.getStatus() == 0) {
            lbBlock.setText("ВАШ АККАУНТ ЗАБЛОКИРОВАН");
            btnReview.setDisable(true);
            btnBooking.setDisable(true);
            btnClientAccount.setDisable(true);
            btnViewRooms.setDisable(true);
        }

        btnViewRooms.setOnAction(actionEvent -> {
            btnViewRooms.getScene().getWindow().hide();

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

        btnClientAccount.setOnAction(actionEvent -> {
            btnClientAccount.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/client-info.fxml"));

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
        btnBooking.setOnAction(actionEvent -> {
            btnBooking.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/client-booking.fxml"));

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
        btnReview.setOnAction(actionEvent -> {
            btnReview.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/client-review.fxml"));

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
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/authorization-view.fxml"));

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
}
