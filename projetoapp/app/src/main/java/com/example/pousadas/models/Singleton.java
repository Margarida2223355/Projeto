package com.example.pousadas.models;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pousadas.db.FoodDBHelper;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.utils.JsonFoodParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class Singleton {
    private ArrayList<Food> foods;
    private FoodDBHelper foodDBHelper = null;
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static String apiUrl = "http://172.22.21.222/Projeto/projetoweb/backend/web/api/refeicaos";
    private FoodsListener foodsListener;

    public static synchronized Singleton getInstance(Context context){
        if (instance == null){
            instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private Singleton(Context context) {
        foods = new ArrayList<>();
        foodDBHelper = new FoodDBHelper(context);
    }

    public void setBooksListener(FoodsListener foodsListener) {
        this.foodsListener = foodsListener;
    }

    public ArrayList<Food> getFoodsDB(){
        foods = foodDBHelper.getAllFoods();
        return new ArrayList<>(foods);
    }

    public Food getFood(int id){
        for (Food food : foods) {
            if (food.getId() == id){
                return food;
            }
        }
        return null;
    }

    /* Para a BD */
    public void addFoodBD(ArrayList<Food> foods){
        foodDBHelper.removeAllFoodsBD();

        for (Food food : foods) {
            addBookDB(food);
        }
    }

    public void addBookDB(Food food){
        foodDBHelper.addFoodDB(food);
    }

    /* Para a API */
    public void getAllFoods(final Context context){
        if (!JsonFoodParser.isConnectionInternet(context)){

            //Listeners will get database info
            if (foodsListener != null) {
                foodsListener.onRefreshFoodsList(foodDBHelper.getAllFoods());
            }
        }else{
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("---->" + response);

                    foods = JsonFoodParser.JsonFoodsParser(response);
                    addFoodBD(foods);
                    if (foodsListener != null){
                        foodsListener.onRefreshFoodsList(foods);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("----> error: " + error);
                }
            });

            volleyQueue.add(request);
        }
    }
}
