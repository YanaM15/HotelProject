package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Role;
import com.bsuir.util.Dialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class RegistrationController {
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
    private Button authorizationBtn;
    @FXML
    private Button registrationBtn;

    @FXML
    void initialize() {
        authorizationBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                authorizationBtn.getScene().getWindow().hide();
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
            }
        });
    }

    @FXML
    void registrationClient(ActionEvent event) throws IOException {
        if (checkInput())
            Dialog.showAlertWithNullInput();
        else {
            Clients clients = new Clients();
            clients.setFirstname(tfName.getText());
            clients.setLastname(tfSurname.getText());
            clients.setLogin(tfLogin.getText());
            clients.setPassword(tfPass.getText());
            clients.setEmail(tfEmail.getText());
            Connect.client.sendMessage("registrationClient");
            Connect.client.sendObject(clients);
            System.out.println("Запись отправлена");

            String mes = "";
            try {
                mes = Connect.client.readMessage();
            } catch(IOException ex){
                System.out.println("Error in reading");
            }
            if (mes.equals("This client is already existed"))
                Dialog.showAlertWithExistLogin();
            else {
                Role r = (Role) Connect.client.readObject();
                Connect.id = r.getId();
                Connect.role = r.getRole();
                registrationBtn.getScene().getWindow().hide();

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
            }
        }
    }

    private boolean checkInput() {
        try {
            return tfName.getText().isEmpty() || tfSurname.getText().isEmpty() ||
                   tfLogin.getText().isEmpty() || tfPass.getText().isEmpty() || tfEmail.getText().isEmpty();
        }
        catch (Exception e) {
            System.out.println("TextField Error");
            return true;
        }
    }
}
