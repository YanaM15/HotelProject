package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Clients;
import com.bsuir.hotelorg.Review;
import com.bsuir.hotelorg.Role;
import com.bsuir.util.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientReviewController implements Initializable {
    @FXML
    private Label lbLogin;
    @FXML
    private Label lbName;
    @FXML
    private Button btnAddReview;
    @FXML
    private TextArea taComment;
    @FXML
    private ComboBox<String> cbRating;
    @FXML
    private Button btnBack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Connect info:");
        System.out.println(Connect.id);
        System.out.println(Connect.role);

        Role r = new Role(Connect.id, Connect.role);
        Connect.client.sendMessage("clientInfo");
        Connect.client.sendObject(r);

        Clients clients = (Clients) Connect.client.readObject();

        //lbLogin.setText(clients.getLogin() + " " + clients.getClientId());
        //lbName.setText(clients.getFirstname() + " " + clients.getLastname());

        cbRating.getItems().addAll("5 stars", "4 stars", "3 stars", "2 stars", "1 star");
        cbRating.setValue("5 stars");

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

        btnAddReview.setOnAction(actionEvent -> {
            if (taComment.getText().isEmpty()) {
                Dialog.incorrectReview();
            } else {
                Review review = new Review();
                review.setComment(taComment.getText());
                String rating = cbRating.getValue();
                int rat = switch (rating) {
                    case "5 stars" -> 5;
                    case "4 stars" -> 4;
                    case "3 stars" -> 3;
                    case "2 stars" -> 2;
                    default -> 1;
                };
                review.setRating(rat);
                review.setClient_id(clients.getClientId());

                Connect.client.sendMessage("addReview");
                Connect.client.sendObject(review);

                String mes = "";
                try {
                    mes = Connect.client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("OK"))
                    Dialog.correctOperation();

                taComment.clear();
            }
        });
    }
}
