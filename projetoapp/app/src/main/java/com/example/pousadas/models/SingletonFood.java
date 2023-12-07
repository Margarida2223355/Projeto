package com.example.pousadas.models;

import java.util.ArrayList;

public class SingletonFood {
    private ArrayList<Food> foods;
    private static SingletonFood instance = null;
    private SingletonFood() { gerarDadosDinamicos(); }

    public static SingletonFood getInstance() {
        if (instance == null) {
            instance = new SingletonFood();
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

    }

    public ArrayList<Food> getFoods() { return foods; }

    public Food getFood(int id) {
        for (Food food : foods) {
            if (food.getId() == id) return food;
        }

        return null;
    }
}
