package com.example.pousadas.models;

import java.util.ArrayList;

public class Singleton {
    private ArrayList<Food> foods;
    private ArrayList<Service> services;
    private static Singleton instance = null;
    private Singleton() { gerarDadosDinamicos(); }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }

    private void gerarDadosDinamicos() {
        foods = new ArrayList<Food>();

        foods.add(new Food(10.5F, "Descrição 1", 1));
        foods.add(new Food(10.5F, "Descrição 2", 2));
        foods.add(new Food(10.5F, "Descrição 3", 3));
        foods.add(new Food(10.5F, "Descrição 4", 4));
        foods.add(new Food(10.5F, "Descrição 5", 5));
        foods.add(new Food(10.5F, "Descrição 6", 6));

        services = new ArrayList<Service>();

        services.add(new Service(10.5F, "Descrição 1", "Nome 1", 1));
        services.add(new Service(10.5F, "Descrição 2", "Nome 2", 2));
        services.add(new Service(10.5F, "Descrição 3", "Nome 3", 3));
        services.add(new Service(10.5F, "Descrição 4", "Nome 4", 4));
        services.add(new Service(10.5F, "Descrição 5", "Nome 5", 5));
        services.add(new Service(10.5F, "Descrição 6", "Nome 6", 6));

    }

    public ArrayList<Food> getFoods() { return foods; }

    public Food getFood(int id) {
        for (Food food : foods) {
            if (food.getId() == id) return food;
        }

        return null;
    }

    public ArrayList<Service> getServices() { return services; }

    public Service getService(int id) {
        for (Service service : services) {
            if (service.getId() == id) return service;
        }

        return null;
    }
}
