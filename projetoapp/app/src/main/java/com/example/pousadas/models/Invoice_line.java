package com.example.pousadas.models;

import com.example.pousadas.enums.Status;

public class Invoice_line {
    private int id, qty;
    private float total, unit_price;
    private Status status;
    private Food food;
    private int reservation_id;
    private Service service;
    private boolean isSelected;

    public Invoice_line(int id, int qty, Service service, Food food, float total, float unit_price, int reservation_id, Status status) {
        this.id = id;
        this.qty = qty;
        this.total = total;
        this.unit_price = unit_price;
        this.status = status;
        this.food = food;
        this.reservation_id = reservation_id;
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

    public int getReservation() {
        return reservation_id;
    }

    public void setReservation(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
