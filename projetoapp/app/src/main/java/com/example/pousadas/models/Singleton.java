package com.example.pousadas.models;

import android.content.Context;

import com.example.pousadas.db.FoodDBHelper;

import java.util.ArrayList;
import java.util.Date;

public class Singleton {
    private ArrayList<Food> foods;
    private FoodDBHelper foodDBHelper = null;
    private static Singleton instance = null;

    public static Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
        }

        return instance;
    }

    private Singleton(Context context) {
        foods = new ArrayList<>();
        foodDBHelper = new FoodDBHelper(context);
    }

    public ArrayList<Food> getFoodsBD() {
        return new ArrayList<>(foodDBHelper.getAllFoods());
    }

    public Food getFood(int id) {
        for (Food food : foods) {
            if (food.getId() == id) return food;
        }

        return null;
    }
}
