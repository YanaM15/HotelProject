package com.bsuir.hotelorg;

import java.io.Serializable;

public class Status implements Serializable {
    private int block;
    private String login;

    public Status() {
        this.block = 0;
        this.login = "";
    }

    public Status(int block, String login) {
        this.block = block;
        this.login = login;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int id) {
        this.block = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Status{" +
                "login=" + login +
                ", block='" + block + '\'' +
                '}';
    }
}
