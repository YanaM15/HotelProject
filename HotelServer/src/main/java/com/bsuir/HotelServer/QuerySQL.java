package com.bsuir.HotelServer;

import com.bsuir.DB.SQLFactory;
import com.bsuir.hotelorg.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuerySQL implements Runnable {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public QuerySQL(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            sois = new ObjectInputStream(clientSocket.getInputStream());
            soos = new ObjectOutputStream(clientSocket.getOutputStream());
            boolean app = true;
            while (app) {
                System.out.println("Получение команды от клиента...");
                String choice = sois.readObject().toString();
                System.out.println(choice);
                System.out.println("Команда получена");

                switch (choice) {
                    case "authorization" -> {
                        System.out.println("Выполняется авторизация пользователя....");
                        Authorization auth = (Authorization) sois.readObject();
                        System.out.println(auth.toString());

                        SQLFactory sqlFactory = new SQLFactory();

                        Role r = sqlFactory.getRole().getRole(auth);
                        System.out.println(r.toString());
                        if (r.getId() != 0 && r.getRole() != "") {
                            soos.writeObject("OK");
                            soos.writeObject(r);
                        } else
                            soos.writeObject("There is no data!");
                    }
                    case "registrationClient" -> {
                        System.out.println("Запрос к БД на проверку пользователя(таблица tb_clients), клиент: " + clientSocket.getInetAddress().toString());
                        Clients client = (Clients) sois.readObject();
                        System.out.println(client.toString());

                        SQLFactory sqlFactory = new SQLFactory();
                        Role r = sqlFactory.getClients().insert(client);
                        System.out.println((r.toString()));

                        if (r.getId() != 0 && r.getRole() != "") {
                            soos.writeObject("OK");
                            soos.writeObject(r);
                        } else {
                            soos.writeObject("This client is already existed");
                        }
                    }
                    case "clientInfo" -> {
                        System.out.println("Запрос к БД на проверку клиента (таблица tb_clients), клиент: " + clientSocket.getInetAddress().toString());
                        Role r = (Role) sois.readObject();
                        System.out.println(r.toString());

                        SQLFactory sqlFactory = new SQLFactory();

                        Clients clients = sqlFactory.getClients().getClient(r);
                        System.out.println(clients.toString());
                        soos.writeObject(clients);
                    }
                    case "clientsList" -> {
                        System.out.println("Запрос к БД на получение информации о клиентах: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Clients> clientsList = sqlFactory.getClients().get();
                        System.out.println(clientsList.toString());
                        soos.writeObject(clientsList);
                    }
                    case "changeClient" -> {
                        System.out.println("Запрос к БД на изменение информации о клиенте: " + clientSocket.getInetAddress().toString());
                        Clients client = (Clients) sois.readObject();

                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getClients().changeClient(client))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при изменении данных клиента");
                    }
                    case "setStatus" -> {
                        System.out.println("Запрос к БД на изменение статуса клиента: " + clientSocket.getInetAddress().toString());
                        Status s = (Status) sois.readObject();
                        System.out.println(s.toString());

                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getClients().setStatus(s))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при изменениии статуса");
                    }
                    case "adminInf" -> {
                        System.out.println("Запрос к БД на получение информации об администраторе: " + clientSocket.getInetAddress().toString());
                        /*SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Admin> infList = sqlFactory.getAdmin().get();
                        System.out.println(infList.toString());
                        soos.writeObject(infList);*/
                    }
                    case "serviceList" -> {
                        System.out.println("Запрос к БД на получение информации об услугах: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Service> servicesList = sqlFactory.getServices().get();
                        System.out.println(servicesList.toString());
                        soos.writeObject(servicesList);
                    }
                    case "addService" -> {
                        System.out.println("Запрос к БД на добавление нового сервиса: " + clientSocket.getInetAddress().toString());
                        Service s = (Service) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getServices().insertService(s))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при добавлении сервиса");
                    }
                    case "roomsList" -> {
                        System.out.println("Запрос к БД на получение информации о типах номеров: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Room> roomsList = sqlFactory.getRooms().get();
                        System.out.println(roomsList.toString());
                        soos.writeObject(roomsList);
                    }
                    case "roomsNumberList" -> {
                        System.out.println("Запрос к БД на получение списка номеров по типу: " + clientSocket.getInetAddress().toString());
                        String s = (String) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Room> roomsList = sqlFactory.getRooms().getRooms(s);
                        System.out.println(roomsList.toString());
                        soos.writeObject(roomsList);
                    }
                    case "changeRoom" -> {
                        System.out.println("Запрос к БД на изменение информации о комнате: " + clientSocket.getInetAddress().toString());
                        Room room = (Room) sois.readObject();

                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getRooms().changeRoom(room))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при изменении данных комнаты");
                    }
                    case "bookingList" -> {
                        System.out.println("Запрос к БД на получение информации о заказах: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Booking> bookingList = sqlFactory.getBooking().get();
                        System.out.println(bookingList.toString());
                        soos.writeObject(bookingList);
                    }
                    case "bookingListClient" -> {
                        System.out.println("Запрос к БД на получение информации о заказах клиента: " + clientSocket.getInetAddress().toString());
                        int id = (Integer) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Booking> bookingList = sqlFactory.getBooking().get(id);
                        System.out.println(bookingList.toString());
                        soos.writeObject(bookingList);
                    }
                    case "bookingCheckRoom" -> {
                        System.out.println("Запрос к БД на возможность нового заказа: " + clientSocket.getInetAddress().toString());
                        Booking booking = (Booking) sois.readObject();

                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getBooking().checkBooking(booking))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Заказ невозможен");
                    }
                    case "bookingPaid" -> {
                        System.out.println("Запрос к БД на оплату заказа: " + clientSocket.getInetAddress().toString());
                        int booking_id = (Integer) sois.readObject();

                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getBooking().changeBooking(booking_id))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при оплате заказа");
                    }
                    case "bookingCancel" -> {
                        System.out.println("Запрос к БД на отмену заказа: " + clientSocket.getInetAddress().toString());
                        int booking_id = (Integer) sois.readObject();

                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getBooking().deleteBooking(booking_id))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при отмене заказа");

                    }
                    case "reviewList" -> {
                        System.out.println("Запрос к БД на получение информации об отзывах: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<Review> reviewList = sqlFactory.getReview().get();
                        System.out.println(reviewList.toString());
                        soos.writeObject(reviewList);
                    }
                    case "addReview" -> {
                        System.out.println("Запрос к БД на добавление нового отзыва: " + clientSocket.getInetAddress().toString());
                        Review r = (Review) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getReview().insertReview(r))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при добавлении сервиса");
                    }
                    case "addBooking" -> {
                        System.out.println("Запрос к БД на добавление нового заказа: " + clientSocket.getInetAddress().toString());
                        Booking booking = (Booking) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getBooking().insertBooking(booking))
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Ошибка при добавлении сервиса");
                    }
                    case "exit" -> {app = false;}
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Client disconnected.");
        }
    }
}
