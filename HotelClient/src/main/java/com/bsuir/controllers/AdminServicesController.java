package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Service;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminServicesController implements Initializable {
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfService;
    @FXML
    private TextField tfPrice;
    @FXML
    private TableView<Service> tvServices;
    @FXML
    private TableColumn<Service, String> tcServName;
    @FXML
    private TableColumn<Service, Integer> tcServPrice;
    @FXML
    private Button btnBack;

    ObservableList<Service> servicesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setOnAction(actionEvent -> {
            if (checkInput())
                Dialog.showAlertWithNullInput();
            else {
                Service s = new Service();
                s.setName(tfService.getText());
                s.setPrice(Integer.parseInt(tfPrice.getText()));
                Connect.client.sendMessage("addService");
                Connect.client.sendObject(s);

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("OK"))
                    Dialog.correctOperation();

                tvServices.getItems().clear();
                tvServices.setItems(getServiceList());

                tfService.clear();
                tfPrice.clear();
            }
        });

        tfPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    tfPrice.setText(t1.replaceAll("[^\\d]", ""));
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

        tcServName.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getName()));
        tcServPrice.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getPrice()));

        tvServices.setItems(getServiceList());
    }

    private ObservableList<Service> getServiceList() {
        Connect.client.sendMessage("serviceList");
        ArrayList<Service> services = (ArrayList<Service>) Connect.client.readObject();
        System.out.println(services);
        servicesList.addAll(services);
        //tvClients.setItems(clientsList);
        return servicesList;
    }

    private boolean checkInput() {
        try {
            return tfService.getText().isEmpty() || tfPrice.getText().isEmpty();
        }
        catch (Exception e) {
            System.out.println("Error");
            return false;
        }
    }
}
