package com.example.pousadas.models;

public class Service {

    /* Propriedades da classe Service:
     *
     * Preço - float
     * Descrição - String
     * Nome - String
     * id - Int
     */
    private float price;
    private String description, name;
    private int id;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Service(float price, String description, String name, int id) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.id = id;
    }

    /* Get e Post
     *
     * Price - get e post
     * Description - get e post
     * Name - get e post
     * Id - get
     */
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
