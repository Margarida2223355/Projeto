package com.example.pousadas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pousadas.enums.Category;
import com.example.pousadas.enums.Status_Res;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Reservation;
import com.example.pousadas.models.Room;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    Geral geral_ = new Geral();

    public class JsonFoodsParser {

        /* Método para obter lista de refeições:
         *
         * Recebe a resposta em formato JSON
         * Converter para lista de refeições
         *
         */
        public ArrayList<Food> jsonFoodsParser(JSONArray response) {
            ArrayList<Food> foods = new ArrayList<>();

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject food = (JSONObject) response.get(i);

                    Food auxFood = new Food(
                            food.getInt("id"),
                            food.getString("nome"),
                            (float) food.getDouble("preco"),
                            geral_.convertToDate(food.getString("data")),
                            Category.getFromString(food.getString("categoria")));

                    foods.add(auxFood);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return foods;
        }
    }

    public class JsonReservationsParser {

        /* Método para obter lista de reservas:
         *
         * Recebe a resposta em formato JSON
         * Converter para lista de reservas
         *
         */
        public ArrayList<Reservation> jsonReservationsParser(JSONArray response) {
            ArrayList<Reservation> reservations = new ArrayList<>();

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject reservation = (JSONObject) response.get(i);
                    JSONObject room = (JSONObject) reservation.getJSONObject("quarto");
                    JSONObject client = (JSONObject) reservation.getJSONObject("cliente");

                    /* Cliente */
                    User auxUser = new User(
                            client.getInt("id"),
                            client.getString("nome_completo"),
                            client.getString("morada"),
                            client.getString("pais"),
                            client.getString("telefone"),
                            (float) client.getDouble("salario"),
                            client.getString("nif")
                    );

                    /* Quarto */
                    Room auxRoom = new Room(
                            room.getInt("id"),
                            room.getString("descricao"),
                            room.getInt("camas_solteiro"),
                            room.getInt("camas_casal"),
                            room.getInt("arcondicionado"),
                            room.getInt("aquecedor"),
                            (float) room.getDouble("preco")
                    );

                    /* Reserva:
                     *
                     * Inclui um quarto e um cliente
                     */
                    Reservation auxReservation = new Reservation(
                            reservation.getInt("id"),
                            geral_.convertToDate(reservation.getString("data_inicial")),
                            geral_.convertToDate(reservation.getString("data_final")),
                            (float) reservation.getDouble("preco_total"),
                            Status_Res.getFromString(reservation.getString("status")),
                            auxUser,
                            auxRoom
                    );

                    reservations.add(auxReservation);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return reservations;
        }
    }

    public class JsonServicesParser {

        /* Método para obter lista de serviços:
         *
         * Recebe a resposta em formato JSON
         * Converter para lista de serviços
         *
         */
        public ArrayList<Service> jsonServicesParser(JSONArray response) {
            ArrayList<Service> services = new ArrayList<>();

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject service = (JSONObject) response.get(i);

                    int idService = service.getInt("id");
                    String nameService = service.getString("nome");
                    String descriptionService = service.getString("descricao");
                    float priceService = (float) service.getDouble("preco");

                    Service auxService = new Service(idService, nameService, descriptionService, priceService);
                    services.add(auxService);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return services;
        }
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
