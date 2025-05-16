package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import com.bsuir.hotelorg.Status;
import com.bsuir.util.Dialog;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminClientsController implements Initializable {

    @FXML
    private TextField tfLogin;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnBlock;
    @FXML
    private TableView<Clients> tvClients;
    @FXML
    private TableColumn<Clients, String> tcLogin;
    @FXML
    private TableColumn<Clients, String> tcPass;
    @FXML
    private TableColumn<Clients, String> tcName;
    @FXML
    private TableColumn<Clients, String> tcSurname;
    @FXML
    private TableColumn<Clients, String> tcEmail;
    @FXML
    private TableColumn<Clients, Integer> tcStatus;

    ObservableList<Clients> clientsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        clientsList.addListener((ListChangeListener<? super Clients>) change -> {
//            tvClients.setItems(clientsList);
//            System.out.println("ListChangeListener");
//        });

        btnBack.setOnAction(actionEvent -> {
            btnBack.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/bsuir/hotelclient/menu-admin.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            clientsList.clear();

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

        btnBlock.setOnAction(actionEvent -> {
            if (tfLogin.getText().isEmpty())
                Dialog.showAlertWithNullInput();
            else {
                Status s = new Status();
                s.setLogin(tfLogin.getText());
                if (Objects.equals(btnBlock.getText(), "Разблокировать"))
                    s.setBlock(1);
                else s.setBlock(0);
                System.out.println(s);
                Connect.client.sendMessage("setStatus");
                Connect.client.sendObject(s);

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("Ошибка при изменениии статуса"))
                    Dialog.showAlertWithNoLogin();

                tvClients.getItems().clear();
                tvClients.setItems(getClientsList());
                tfLogin.clear();
                btnBlock.setText("Заблокировать/Разблокировать");
            }
        });

        tvClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clients>() {
            @Override
            public void changed(ObservableValue<? extends Clients> observableValue, Clients clients, Clients t1) {
                if (t1 != null) {
                    tfLogin.setText(t1.getLogin());
                    if (t1.getStatus() == 0)
                        btnBlock.setText("Разблокировать");
                    else btnBlock.setText("Заблокировать");
                }
            }
        });

        tcLogin.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getLogin()));
        tcPass.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPassword()));
        tcName.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getFirstname()));
        tcSurname.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getLastname()));
        tcEmail.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getEmail()));
        tcStatus.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getStatus()));

        tvClients.setItems(getClientsList());


    }

    private ObservableList<Clients> getClientsList() {
        Connect.client.sendMessage("clientsList");
        ArrayList<Clients> clients = (ArrayList<Clients>) Connect.client.readObject();
        System.out.println(clients);
        clientsList.addAll(clients);
        //tvClients.setItems(clientsList);
        return clientsList;
    }
}
