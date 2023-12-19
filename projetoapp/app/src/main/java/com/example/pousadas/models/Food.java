package com.example.pousadas.models;

import java.util.Date;

public class Food {

    /* Propriedades da classe Food:
     *
     * Preço - float
     * Nome - String
     * Id - Int
     * Date - Date
     * Hour - String (enum - "Jantar" ou "Almoço")
     */
    private int id, qty;
    private String name, hour;
    private float price;
    private Date date;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Food(int id, String name, float price, Date date, String hour) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.hour = hour;
        qty = 1;
    }

    /* Get e Post
     *
     * Price - get e post
     * Name - get e post
     * Id - get
     * Qty - get
     * Date - get
     * Hour - get
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

    public String getHour() {
        return hour;
    }

    public Date getDate() {
        return date;
    }

    /* Métodos para Incrementar ou Decrementar quantidades */
    public void addQty() {
        qty++;
    }

    public void remQty() {
        if (qty > 1) qty--;
    }
}
