package com.bsuir.hotelorg;

import java.io.Serializable;
import java.sql.Date;


public class Booking implements Serializable {
    private int id;
    private int client_id;
    private String client_name;
    private String client_surname;
    private int room_id;
    private int room_number;
    private String room_type;
    private int room_level;
    private int serv_id;
    private String serv_name;
    private Date check_in;
    private Date check_out;
    private int status;
    private int cost;


    public Booking() {
        this.id = -1;
        this.client_id = -1;
        this.client_name = "";
        this.client_surname = "";
        this.room_id = -1;
        this.room_number = -1;
        this.room_type = "";
        this.room_level = -1;
        this.serv_id = -1;
        this.serv_name = "";
        this.check_in = Date.valueOf("2025-02-07");;
        this.check_out = Date.valueOf("2025-02-09");;
        this.status = 0;
        this.cost = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_surname() {
        return client_surname;
    }

    public void setClient_surname(String client_surname) {
        this.client_surname = client_surname;
    }

    public String getClient_fullname() {
        return client_surname + " " + client_name;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_number() {
        return room_number;
    }

    public String getRoom_number_str() {
        return "" + room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getRoom_type() { return room_type; }

    public void setRoom_type(String room_type) { this.room_type = room_type; }

    public int getRoom_level() {
        return room_level;
    }

    public void setRoom_level(int room_level) {
        this.room_level = room_level;
    }

    public int getServ_id() {
        return serv_id;
    }

    public void setServ_id(int serv_id) {
        this.serv_id = serv_id;
    }

    public String getServ_name() {
        return serv_name;
    }

    public void setServ_name(String serv_name) {
        this.serv_name = serv_name;
    }

    public Date getCheck_in() {
        return check_in;
    }

    public String getCheck_in_str() {
        return check_in.toString();
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public void setCheck_in_str(String date) {
        this.check_in = Date.valueOf(date);
    }

    public Date getCheck_out() {
        return check_out;
    }

    public String getCheck_out_str() {
        return check_out.toString();
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }

    public void setCheck_out_str(String date) {
        this.check_out = Date.valueOf(date);
    }

    public int getStatus() {
        return status;
    }

    public String getStatus_str() {
        return switch (status) {
            case 1 -> "бронь";
            case 2 -> "оплачен";
            default -> "-";
        };
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", client_id='" + client_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", check_in='" + getCheck_in_str() + '\'' +
                ", check_out='" + getCheck_out_str() + '\'' +
                ", status='" + status + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}

