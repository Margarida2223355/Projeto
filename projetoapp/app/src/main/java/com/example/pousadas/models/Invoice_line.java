package com.example.pousadas.models;

import com.example.pousadas.enums.Status;

public class Invoice_line {
    private int id, qty;
    private float total, unit_price;
    private Status status;
    private Food food;
    private Reservation reservation;
    private Service service;
}
