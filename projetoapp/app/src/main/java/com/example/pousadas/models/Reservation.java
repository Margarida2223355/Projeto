package com.example.pousadas.models;

import com.example.pousadas.enums.Status;
import com.example.pousadas.enums.Status_Res;

import java.util.Date;

public class Reservation {
    private int id;
    private Date init_date, end_date;
    private float total_price;
    private Status_Res status_res;
    private User client;
    private Room room;

    public Reservation(int id, Date init_date, Date end_date, float total_price, Status_Res status_res, User client, Room room) {
        this.id = id;
        this.init_date = init_date;
        this.end_date = end_date;
        this.total_price = total_price;
        this.status_res = status_res;
        this.client = client;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getInit_date() {
        return init_date;
    }

    public void setInit_date(Date init_date) {
        this.init_date = init_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public Status_Res getStatus() {
        return status_res;
    }

    public void setStatus(Status_Res status) {
        this.status_res = status_res;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
