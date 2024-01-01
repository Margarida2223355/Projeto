package com.example.pousadas.models;

public class Room {
    private int id, single_bed, couple_bed, aircond, heater;
    private String descricao;
    private float price;

    public Room(int id, String descricao, int single_bed, int couple_bed, int aircond, int heater, float price) {
        this.id = id;
        this.descricao = descricao;
        this.single_bed = single_bed;
        this.couple_bed = couple_bed;
        this.aircond = aircond;
        this.heater = heater;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getSingle_bed() {
        return single_bed;
    }

    public void setSingle_bed(int single_bed) {
        this.single_bed = single_bed;
    }

    public int getCouple_bed() {
        return couple_bed;
    }

    public void setCouple_bed(int couple_bed) {
        this.couple_bed = couple_bed;
    }

    public int getAircond() {
        return aircond;
    }

    public void setAircond(int aircond) {
        this.aircond = aircond;
    }

    public int getHeater() {
        return heater;
    }

    public void setHeater(int heater) {
        this.heater = heater;
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
