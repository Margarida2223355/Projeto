package com.example.pousadas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pousadas.enums.Category;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodJsonParser {

    /* Método para obter lista de refeições:
     *
     * Recebe a resposta em formato JSON
     * Converter para lista de refeições
     *
     */
    public static ArrayList<Food> jsonFoodsParser(JSONArray response) {
        Geral geral_ = new Geral();
        ArrayList<Food> foods = new ArrayList<>();

        try {
            for (int i=0; i<response.length(); i++) {
                JSONObject food = (JSONObject) response.get(i);

                Food auxFood = new Food(
                        food.getInt("id"),
                        food.getString("nome"),
                        (float) food.getDouble("preco"),
                        geral_.convertToDate(food.getString("data")),
                        Category.getFromString(food.getString("categoria")));

                        foods.add(auxFood);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return foods;
    }

    /* Método para verificar se ligação à internet foi realizada */
    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return
                networkInfo != null
                &&
                networkInfo.isConnectedOrConnecting();
    }
}
