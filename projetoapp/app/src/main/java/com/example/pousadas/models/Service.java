package com.example.pousadas.models;

public class Service {

    /* Propriedades da classe Service:
     *
     * Preço - float
     * Descrição - String
     * Nome - String
     * id - Int
     * qty - int
     */
    private float price;
    private String description, name;
    private int id, qty;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Service(int id, String name, String description, float price) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.id = id;
        this.qty = 0;
    }

    /* Get e Post
     *
     * Price - get e post
     * Description - get e post
     * Name - get e post
     * Id - get
     * Qty - get
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

    public int getQty() {
        return qty;
    }

    /* Métodos para Incrementar ou Decrementar quantidades */
    public void addQty() {
        qty++;
    }

    public void remQty() {
        if (qty > 0) qty--;
    }
}
