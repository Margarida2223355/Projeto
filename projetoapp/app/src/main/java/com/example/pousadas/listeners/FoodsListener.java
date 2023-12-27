package com.example.pousadas.listeners;

import com.example.pousadas.models.Food;

import java.util.ArrayList;

public interface FoodsListener {
    void onRefreshFoodsList(ArrayList<Food> foods);
}
