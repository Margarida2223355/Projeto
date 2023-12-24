package com.example.pousadas.models;

import com.example.pousadas.enums.Schedule;

import java.util.ArrayList;
import java.util.Date;

public class Singleton {
    private Geral geral_ = new Geral();
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

        foods.add(new Food(1, "Descrição 1", 10.5F, geral_.convertToDate("20/12/2023"), Schedule.ALMOCO));
        foods.add(new Food(2, "Descrição 2", 10.5F, geral_.convertToDate("19/12/2023"), Schedule.ALMOCO));
        foods.add(new Food(3, "Descrição 3", 10.5F, geral_.getFromDate(new Date()), Schedule.ALMOCO));
        foods.add(new Food(4, "Descrição 4", 10.5F, geral_.convertToDate("20/12/2023"), Schedule.JANTAR));
        foods.add(new Food(5, "Descrição 5", 10.5F, geral_.convertToDate("19/12/2023"), Schedule.JANTAR));
        foods.add(new Food(6, "Descrição 6", 10.5F, geral_.getFromDate(new Date()), Schedule.JANTAR));

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

    /* Obter lista de refeições com base na data e horário selecionados */
    public ArrayList<Food> getFoodsByDateSchedule(Date date, Schedule schedule) {
        ArrayList<Food> listFood = new ArrayList<>();

        for (Food food : foods) {
            if ((geral_.getFromDate(food.getDate()).equals(geral_.getFromDate(date))) && (food.getSchedule() == schedule)) {
                listFood.add(food);
            }
        }

        return listFood;
    }

    public ArrayList<Service> getServices() { return services; }

    public Service getService(int id) {
        for (Service service : services) {
            if (service.getId() == id) return service;
        }

        return null;
    }
}
