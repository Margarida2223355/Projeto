package com.example.pousadas.models;

import java.util.Date;

public class Invoice {
    private int id;
    private Date payment_date;
    private float total_price;
    private int reservation_id;
    private int lodge_id;

    public Invoice(int id, Date payment_date, float total_price, int reservation_id, int lodge_id) {
        this.id = id;
        this.payment_date = payment_date;
        this.total_price = total_price;
        this.reservation_id = reservation_id;
        this.lodge_id = lodge_id;
    }

    public int getId() {
        return id;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getReservation() {
        return reservation_id;
    }

    public void setReservation(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getLodge() {
        return lodge_id;
    }

    public void setLodge(int lodge_id) {
        this.lodge_id = lodge_id;
    }
}
