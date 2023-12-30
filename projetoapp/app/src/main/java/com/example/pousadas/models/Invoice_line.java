package com.example.pousadas.models;

import com.example.pousadas.enums.Status;

public class Invoice_line {
    private int id, qty;
    private float total, unit_price;
    private Status status;
    private Food food;
    private Reservation reservation;
    private Service service;

    public Invoice_line(int id, int qty, float total, float unit_price, Status status, Food food, Reservation reservation, Service service) {
        this.id = id;
        this.qty = qty;
        this.total = total;
        this.unit_price = unit_price;
        this.status = status;
        this.food = food;
        this.reservation = reservation;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
