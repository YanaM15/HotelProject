package com.bsuir.controllers;

import com.bsuir.client.Connect;
import com.bsuir.hotelorg.Booking;
import com.bsuir.hotelorg.Review;
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

public class AdminReviewsController implements Initializable {
    @FXML
    private TableView<Review> tvReviews;
    @FXML
    private TableColumn<Review, String> tcClient;
    @FXML
    private TableColumn<Review, Integer> tcRating;
    @FXML
    private TableColumn<Review, String> tcReview;
    @FXML
    private Button btnBack;

    ObservableList<Review> reviewList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        tcClient.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getClient_fullname()));
        tcRating.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getRating()));
        tcReview.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getComment()));

        tvReviews.setItems(getReviewList());
    }

    private ObservableList<Review> getReviewList() {
        Connect.client.sendMessage("reviewList");
        ArrayList<Review> reviews = (ArrayList<Review>) Connect.client.readObject();
        System.out.println(reviews);
        reviewList.addAll(reviews);

        return reviewList;
    }
}
