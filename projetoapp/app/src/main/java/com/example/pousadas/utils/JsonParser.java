package com.example.pousadas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pousadas.enums.Category;
import com.example.pousadas.enums.Status;
import com.example.pousadas.enums.Status_Res;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Invoice;
import com.example.pousadas.models.Invoice_line;
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

        public Reservation jsonReservationParser(JSONObject response) {
            Reservation auxReservation = null;

            try {
                JSONObject reservation = response;
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
                auxReservation = new Reservation(
                        reservation.getInt("id"),
                        geral_.convertToDate(reservation.getString("data_inicial")),
                        geral_.convertToDate(reservation.getString("data_final")),
                        (float) reservation.getDouble("preco_total"),
                        Status_Res.getFromString(reservation.getString("status")),
                        auxUser,
                        auxRoom
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return auxReservation;
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

    public class JsonUserParser {
        public User jsonUserParser(JSONObject response) {
            User user = null;

            try {
                user = new User(
                        response.getInt("id"),
                        response.getString("nome_completo"),
                        response.getString("morada"),
                        response.getString("pais"),
                        response.getString("telefone"),
                        (float) response.getDouble("salario"),
                        response.getString("nif")
                );
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            return  user;
        }
    }

    public class JsonLineParser {
        public ArrayList<Invoice_line> jsonLinesParser(JSONArray response) {
            ArrayList<Invoice_line> lines = new ArrayList<>();

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject line = (JSONObject) response.get(i);
                    JSONObject service;
                    JSONObject food;

                    Service auxService = null;
                    Food auxFood = null;

                    if (line.optJSONObject("servico") != null) {
                        service = (JSONObject) line.getJSONObject("servico");
                        auxService = new Service(
                                service.getInt("id"),
                                service.getString("nome"),
                                service.getString("descricao"),
                                (float) service.getDouble("preco"));
                    }

                    if (line.optJSONObject("refeicao") != null) {
                        food = (JSONObject) line.getJSONObject("refeicao");
                        auxFood = new Food(
                                food.getInt("id"),
                                food.getString("nome"),
                                (float) food.getDouble("preco"),
                                geral_.convertToDate(food.getString("data")),
                                Category.getFromString(food.getString("categoria")));

                    }

                    Invoice_line auxLine = new Invoice_line(
                            line.getInt("id"),
                            line.getInt("quantidade"),
                            auxService,
                            auxFood,
                            (float) line.getDouble("sub_total"),
                            (float) line.getDouble("preco_unitario"),
                            line.getInt("reserva_id"),
                            Status.getFromString(line.getString("status"))
                    );

                    lines.add(auxLine);
                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            return  lines;
        }
        public Invoice_line jsonLineParser(String response) {
            Invoice_line auxLine = null;

            try {
                JSONObject line = new JSONObject(response).getJSONObject("line");
                JSONObject service;
                JSONObject food;

                Service auxService = null;
                Food auxFood = null;

                if (line.optJSONObject("servico") != null) {
                    service = (JSONObject) line.getJSONObject("servico");
                    auxService = new Service(
                            service.getInt("id"),
                            service.getString("nome"),
                            service.getString("descricao"),
                            (float) service.getDouble("preco"));
                }

                if (line.optJSONObject("refeicao") != null) {
                    food = (JSONObject) line.getJSONObject("refeicao");
                    auxFood = new Food(
                            food.getInt("id"),
                            food.getString("nome"),
                            (float) food.getDouble("preco"),
                            geral_.convertToDate(food.getString("data")),
                            Category.getFromString(food.getString("categoria")));

                }

                auxLine = new Invoice_line(
                        line.getInt("id"),
                        line.getInt("quantidade"),
                        auxService,
                        auxFood,
                        (float) line.getDouble("sub_total"),
                        (float) line.getDouble("preco_unitario"),
                        line.getInt("reserva_id"),
                        Status.getFromString(line.getString("status"))
                );
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            return auxLine;
        }
    }

    public class JsonInvoiceParser {
        public Invoice jsonInvoiceParser(String response) {
            Invoice auxInvoice = null;

            try {
                JSONObject invoice = new JSONObject(response).getJSONObject("fatura");

                auxInvoice = new Invoice(
                        invoice.getInt("id"),
                        geral_.convertToDate("data_pagamento"),
                        (float) invoice.getDouble("preco_total"),
                        invoice.getInt("reserva_id"),
                        invoice.getInt("pousada_id")
                );

            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            return auxInvoice;
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
