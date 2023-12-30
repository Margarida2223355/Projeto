package com.example.pousadas.models;

public class Room {
    private int id;
    private String descricao;
    private float price;

    public Room(int id, String descricao, float price) {
        this.id = id;
        this.descricao = descricao;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
