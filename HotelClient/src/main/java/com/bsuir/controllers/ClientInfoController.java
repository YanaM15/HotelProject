package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import com.bsuir.util.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientInfoController implements Initializable {
    @FXML
    private Label lbLogin;
    @FXML
    private Label lbName;
    @FXML
    private Button btnSave;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnBack;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Получение данных клиента
        Role r = new Role(Connect.id, Connect.role);
        Connect.client.sendMessage("clientInfo");
        Connect.client.sendObject(r);
        Clients clients = (Clients) Connect.client.readObject();
        tfLogin.setText(clients.getLogin());
        tfPass.setText(clients.getPassword());
        tfName.setText(clients.getFirstname());
        tfSurname.setText(clients.getLastname());
        tfEmail.setText(clients.getEmail());
        lbLogin.setText(clients.getLogin());
        lbName.setText(clients.getLastname() + " " + clients.getFirstname());

        //Кнопка Сохранить изменения
        btnSave.setOnAction(actionEvent -> {
            if (checkInput())
                Dialog.showAlertWithNullInput();
            else {
                clients.setPassword(tfPass.getText());
                clients.setFirstname(tfName.getText());
                clients.setLastname(tfSurname.getText());
                clients.setEmail(tfEmail.getText());

                Connect.client.sendMessage("changeClient");
                Connect.client.sendObject(clients);

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("Ошибка при изменении данных клиента"))
                    Dialog.showAlertSQL();
                else Dialog.correctOperation();
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
    }

    private boolean checkInput() {
        try {
            return tfPass.getText().isEmpty() || tfName.getText().isEmpty() ||
                    tfSurname.getText().isEmpty() || tfEmail.getText().isEmpty();
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
