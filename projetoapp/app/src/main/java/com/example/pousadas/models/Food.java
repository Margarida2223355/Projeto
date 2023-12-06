package com.example.pousadas.models;

public class Food {

    /* Propriedades da classe Food:
     *
     * Preço - float
     * Nome - String
     * id - Int
     */
    private float price;
    private String name;
    private int id;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Food(float price, String name, int id) {
        this.price = price;
        this.name = name;
        this.id = id;
    }

    /* Get e Post
     *
     * Price - get e post
     * Name - get e post
     * Id - get
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
}
