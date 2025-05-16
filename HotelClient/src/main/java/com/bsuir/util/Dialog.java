package com.bsuir.util;

import javafx.scene.control.Alert;

public class Dialog {
    static public void showAlertWithNullInput(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Ввод данных");
        alert.setContentText("Заполните пустые поля");
        alert.showAndWait();
    }

    static public void showAlertWithExistLogin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Регистрация");
        alert.setContentText("Такой пользователь уже существует");
        alert.showAndWait();
    }

    static public void showAlertWithNoLogin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Введите правильно логин или пароль");
        alert.setContentText("Такой пользователь не найден в системе");
        alert.showAndWait();
    }

    static public void showAlertWithData(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Сбой задачи");
        alert.setContentText("Проверьте введнные параметры");
        alert.showAndWait();
    }

    static public void correctOperation(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Correct");
        alert.setHeaderText("");
        alert.setContentText("Операция прошла успешно");
        alert.showAndWait();
    }

    static public void incorrectSearch(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incorrect");
        alert.setHeaderText("Данные отсутствуют");
        alert.setContentText("Введите другие данные для поиска");
        alert.showAndWait();
    }

    static public void incorrectReview(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incorrect Review");
        alert.setHeaderText("Данные отсутствуют");
        alert.setContentText("Напишите пару слов, нам будет приятно!");
        alert.showAndWait();
    }

    static public void roomNotSelected(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Type of room not selected");
        alert.setHeaderText("Номер не выбран");
        alert.setContentText("Для бронирования выберите тип номера в таблице!");
        alert.showAndWait();
    }

    static public void bookingPaid(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Booking Paid");
        alert.setHeaderText("Заказ оплачен");
        alert.setContentText("Забронированный номер уже оплачен!");
        alert.showAndWait();
    }

    static public void showAlertSQL(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка SQL");
        alert.setContentText("Ошибка чтения/записи данных");
        alert.showAndWait();
    }

    static public void showAlertDate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка даты бронирования");
        alert.setContentText("Дата бронирования не должна быть меньше текущей");
        alert.showAndWait();
    }

    static public void showAlertBooking(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка бронирования");
        alert.setContentText("Выбранный номер забронирован на указанный период.");
        alert.showAndWait();
    }
}
