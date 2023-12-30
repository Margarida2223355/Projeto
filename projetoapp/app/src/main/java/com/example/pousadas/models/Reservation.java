package com.example.pousadas.models;

import com.example.pousadas.enums.Status;

import java.util.Date;

public class Reservation {
    private int id;
    private Date init_date, end_date;
    private float total_price;
    private Status status;
    private User client;
    private Room room;
}
