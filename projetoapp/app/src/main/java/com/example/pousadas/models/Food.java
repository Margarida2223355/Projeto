package com.example.pousadas.models;

import com.example.pousadas.enums.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Food {

    /* Propriedades da classe Food:
     *
     * Preço - float
     * Nome - String
     * id - Int
     * Data - Date
     * Horário - Schedule
     */
    private float price;
    private String name;
    private int id, qty;
    private Date date;
    private Schedule schedule;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Food(int id, String name, Float price, Date date, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.schedule = schedule;
        qty = 0;
    }

    /* Get e Post
     *
     * Price - get e post
     * Name - get e post
     * Id - get
     * Qty - get
     * Date - get e post
     * Schedule - get e post
     */

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /* Métodos para Incrementar ou Decrementar quantidades */
    public void addQty() {
        qty++;
    }

    public void remQty() {
        if (qty > 0) qty--;
    }
}
