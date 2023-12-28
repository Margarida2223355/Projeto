package com.example.pousadas.models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pousadas.enums.Schedule;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.utils.FoodJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class Singleton {
    private Geral geral_ = new Geral();
    private ArrayList<Food> foods;
    private ArrayList<Service> services;
    private static Singleton instance;
    private FoodsListener foodsListener;
    private FoodDBHelper foodDBHelper;
    private static RequestQueue volleyQueue;
    private static String apiUrl = "http://192.168.1.91/Projeto/projeto/backend/web/api/refeicaos";

    public static synchronized Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private Singleton(Context context) {
        foods = new ArrayList<>();
        foodDBHelper = new FoodDBHelper(context);
    }

    public void setFoodsListener(FoodsListener foodsListener) {
        this.foodsListener = foodsListener;
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

    /* API */
    /* Obter lista de refeições com base na data e horário selecionados */
    public void getFoodsByDateSchedule(Date date, Schedule schedule, final Context context) {
        ArrayList<Food> resultFoods = new ArrayList<>();

        if (!FoodJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (foodsListener != null) {
                foodsListener.onRefreshFoodsList(foodDBHelper.getAllFoods());
            }
        }

        else {
            apiUrl = "http://192.168.1.91/Projeto/projeto/backend/web/api/refeicaos/" + geral_.convertFromDate(geral_.getFromDate(date)) + "/" + schedule.getSchedule();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("--> " + response);

                    foods = FoodJsonParser.jsonFoodsParser(response);
                    addFoodsDB(foods);

                    if (foodsListener != null) {
                        foodsListener.onRefreshFoodsList(foods);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> " + error);
                }
            });

            volleyQueue.add(request);
        }
    }

    private void addFoodsDB(ArrayList<Food> foods) {
        foodDBHelper.removeAllFoodsDB();

        for (Food food : foods) {
            addFoodDB(food);
        }
    }

    private void addFoodDB(Food food) {
        foodDBHelper.addFoodDB(food);
    }
}
