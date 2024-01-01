package com.example.pousadas.models;

import java.util.Date;

public class Invoice {
    private int id;
    private Date payment_date = null;
    private float total_price = .0F, total_w_out_tax= .0F;
    private Reservation reservation;
    private Lodge lodge;
    private Invoice_line invoiceLine;

    public Invoice(int id, Reservation reservation, Lodge lodge, Invoice_line invoiceLine) {
        this.id = id;
        this.reservation = reservation;
        this.lodge = lodge;
        this.invoiceLine = invoiceLine;
    }

    public Invoice(int id, Date payment_date, float total_price, float total_w_out_tax, Reservation reservation, Lodge lodge, Invoice_line invoiceLine) {
        this.id = id;
        this.payment_date = payment_date;
        this.total_price = total_price;
        this.total_w_out_tax = total_w_out_tax;
        this.reservation = reservation;
        this.lodge = lodge;
        this.invoiceLine = invoiceLine;
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

    public float getTotal_w_out_tax() {
        return total_w_out_tax;
    }

    public void setTotal_w_out_tax(float total_w_out_tax) {
        this.total_w_out_tax = total_w_out_tax;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Lodge getLodge() {
        return lodge;
    }

    public void setLodge(Lodge lodge) {
        this.lodge = lodge;
    }

    public Invoice_line getInvoiceLine() {
        return invoiceLine;
    }

    public void setInvoiceLine(Invoice_line invoiceLine) {
        this.invoiceLine = invoiceLine;
    }
}
