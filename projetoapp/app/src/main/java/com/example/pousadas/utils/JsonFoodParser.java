package com.example.pousadas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class JsonFoodParser {
    public static ArrayList<Food> JsonFoodsParser(JSONArray response){
        ArrayList<Food> foods = new ArrayList<>();

        try {
            for (int i = 0; i<response.length(); i++){
                JSONObject food = (JSONObject) response.get(i);

                int idFood = food.getInt("id");
                String nameFood = food.getString("nome");
                float priceFood = (float) food.getDouble("preco");
                Date dateFood = new Geral().getDateFromString(food.getString("data"));
                String hourFood = food.getString("horario");

                Food auxFood = new Food(idFood, nameFood, priceFood, dateFood, hourFood);
                foods.add(auxFood);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return foods;
    }

    public static Food jsonFoodParser(String response) {
        Food auxFood = null;

        try {
            JSONObject food = new JSONObject(response);

            int idFood = food.getInt("id");
            String nameFood = food.getString("nome");
            float priceFood = (float) food.getDouble("preco");
            Date dateFood = new Geral().getDateFromString(food.getString("data"));
            String hourFood = food.getString("horario");

            auxFood = new Food(idFood, nameFood, priceFood, dateFood, hourFood);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return auxFood;
    }

    public static String jsonLoginParser(String response){

        String token = null;

        try {
            JSONObject login = new JSONObject(response);

            if (login.getBoolean("success")){
                token = login.getString("token");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return token;
    }

    public static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return
                networkInfo != null
                &&
                networkInfo.isConnectedOrConnecting();

    }
}
