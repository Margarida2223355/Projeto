package com.example.pousadas.models;

import com.example.pousadas.enums.Category;

import java.util.Date;


public class Food {

    /* Propriedades da classe Food:
     *
     * Preço - float
     * Nome - String
     * id - Int
     * Data - Date
     * Horário - Category
     */
    private float price;
    private String name;
    private int id, qty;
    private Date date;
    private Category category;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Food(int id, String name, Float price, Date date, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
        qty = 0;
    }

    /* Get e Post
     *
     * Price - get e post
     * Name - get e post
     * Id - get
     * Qty - get
     * Date - get e post
     * Category - get e post
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /* Métodos para Incrementar ou Decrementar quantidades */
    public void addQty() {
        qty++;
    }

    public void remQty() {
        if (qty > 0) qty--;
    }

    public float getTotal() {
        return
                qty * price;
    }
}
