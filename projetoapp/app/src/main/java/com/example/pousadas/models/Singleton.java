package com.example.pousadas.models;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pousadas.activities.IPConfigActivity;
import com.example.pousadas.activities.LoginActivity;
import com.example.pousadas.db.DBHelper;
import com.example.pousadas.enums.Category;
import com.example.pousadas.enums.Status;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.listeners.LinesListener;
import com.example.pousadas.listeners.ReservationsListener;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.listeners.UserListener;
import com.example.pousadas.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Singleton extends AppCompatActivity {
    private Geral geral_ = new Geral();
    private ArrayList<Food> foods;
    private ArrayList<Service> services;
    private ArrayList<Reservation> reservations;
    private ArrayList<Invoice_line> lines;
    private static Singleton instance;
    private FoodsListener foodsListener;
    private ServicesListener servicesListener;
    private ReservationsListener reservationsListener;
    private UserListener userListener;
    private LinesListener linesListener;
    private DBHelper dbHelper;
    private DBHelper.FoodsTable foodsTable;
    private DBHelper.ServicesTable servicesTable;
    private DBHelper.ReservationsTable reservationsTable;
    private DBHelper.UserTable userTable;
    private DBHelper.InvoiceLineTable lineTable;
    private DBHelper.InvoiceTable invoiceTable;
    private JsonParser jsonParser = new JsonParser();
    private static RequestQueue volleyQueue;
    private SharedPreferences ipPreferences, userPreferences;
    private static final String PROJECTURL = "/Projeto/projeto/backend/web/api/";
    private String INIT_URL;
    public static synchronized Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private Singleton(Context context) {
        foods = new ArrayList<>();
        services = new ArrayList<>();
        reservations = new ArrayList<>();
        lines = new ArrayList<>();
        dbHelper = new DBHelper(context);
        foodsTable = dbHelper.new FoodsTable();
        servicesTable = dbHelper.new ServicesTable();
        reservationsTable = dbHelper.new ReservationsTable();
        userTable = dbHelper.new UserTable();
        lineTable = dbHelper.new InvoiceLineTable();
        invoiceTable = dbHelper.new InvoiceTable();

        ipPreferences = context.getSharedPreferences(IPConfigActivity.IPCONFIG, Context.MODE_PRIVATE);
        userPreferences = context.getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);

        INIT_URL = "http://"
                + ipPreferences.getString(IPConfigActivity.IP, "")
                + PROJECTURL;
    }

    public void setFoodsListener(FoodsListener foodsListener) {
        this.foodsListener = foodsListener;
    }

    public void setServicesListener(ServicesListener servicesListener) {
        this.servicesListener = servicesListener;
    }

    public void setReservationsListener(ReservationsListener reservationsListener) {
        this.reservationsListener = reservationsListener;
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    public void setLinesListener(LinesListener linesListener) {
        this.linesListener = linesListener;
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

    public ArrayList<Reservation> getReservations() { return reservations; }

    public Reservation getReservation(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) return reservation;
        }

        return null;
    }

    public Invoice_line getLine(int id) {
        for (Invoice_line line : lineTable.getAllLines()) {
            if (line.getId() == id) {
                return line;
            }
        }

        return null;
    }


    /* API */
    /* Obter lista de refeições com base na data e horário selecionados */
    public void getFoodsByDateSchedule(Date date, Category category, final Context context) {

        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (foodsListener != null) {
                foodsListener.onRefreshFoodsList(foodsTable.getAllFoods());
            }
        }

        else {
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    INIT_URL + "refeicaos/" + geral_.convertFromDate(geral_.getFromDate(date)) + "/" + category.getCategory(),
                    null,
                    new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("--> " + response);

                    foods = jsonParser.new JsonFoodsParser().jsonFoodsParser(response);
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
        foodsTable.removeAllFoodsDB();

        for (Food food : foods) {
            addFoodDB(food);
        }
    }

    private void addFoodDB(Food food) {
        foodsTable.addFoodDB(food);
    }

    public void getAllServices(final Context context) {
        if (!jsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (servicesListener != null) {
                servicesListener.onRefreshServicesList(servicesTable.getAllServices());
            }
        }

        else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                    INIT_URL + "servicos",
                null,
                new Response.Listener<JSONArray>()
                {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("--> " + response);

                    services = jsonParser.new JsonServicesParser().jsonServicesParser(response);
                    addServicesDB(services);

                    if (servicesListener != null) {
                        servicesListener.onRefreshServicesList(services);
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

    private void addServicesDB(ArrayList<Service> services) {
        servicesTable.removeAllServicesDB();

        for (Service service : services) {
            addServiceDB(service);
        }
    }

    private void addServiceDB(Service service) {
        servicesTable.addServiceDB(service);
    }

    public void getReservationsByDates(Date initDate, Date endDate, final Context context) {
        if (!jsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (reservationsListener != null) {
                reservationsListener.onRefreshReservationsList(reservationsTable.getAllReservations());
            }
        }

        else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                    INIT_URL + "reservas/" + geral_.convertFromDate(geral_.getFromDate(initDate)) + "/" + geral_.convertFromDate(geral_.getFromDate(endDate)) + "/" + userPreferences.getInt(LoginActivity.USER_ID, 0),
                    null,
                    new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("--> " + response);

                    reservations = jsonParser.new JsonReservationsParser().jsonReservationsParser(response);
                    addReservationsDB(reservations);

                    if (reservationsListener != null) {
                        reservationsListener.onRefreshReservationsList(reservations);
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

    public void getReservationAtive(final Context context) {
        if (!jsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (reservationsListener != null) {
                ArrayList<Reservation> auxReservations = reservationsTable.getAllReservations();
                Reservation reservation = null;

                for (Reservation res : auxReservations) {
                    if (res.getClient().getId() == userPreferences.getInt(LoginActivity.USER_ID, 0)) {
                        reservation = res;
                    }
                }

                reservationsListener.onRefreshReservationActive(reservation);
            }
        }

        else {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    INIT_URL + "reservas/" + userPreferences.getInt(LoginActivity.USER_ID, 0) + "/reserva",
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("--> " + response);

                            Reservation reservation = jsonParser.new JsonReservationsParser().jsonReservationParser(response);

                            if (reservationsListener != null) {
                                reservationsListener.onRefreshReservationActive(reservation);
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

    private void addReservationsDB(ArrayList<Reservation> reservations) {
        reservationsTable.removeAllReservationsDB();

        for (Reservation reservation : reservations) {
            addReservationDB(reservation);
        }
    }

    private void addReservationDB(Reservation reservation) {
        reservationsTable.addReservationDB(reservation);
    }

    public void getUser(String username, String password, final Context context) {
        if (!jsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (userListener != null) {
                userListener.onLogin(userTable.getUser());
            }
        }

        else {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    INIT_URL + "users/login",
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("--> " + response);

                            User user = jsonParser.new JsonUserParser().jsonUserParser(response);
                            addUserDB(user);

                            if (userListener != null) {
                                userListener.onLogin(user);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> " + error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = username + ":" + password;
                    String base64Credentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", "Basic " + base64Credentials);
                    return headers;
                }
            };

            volleyQueue.add(request);
        }
    }

    public void addUserDB(User user) {
        userTable.addUserDB(user);
    }

    public void getLines(final Context context) {

        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (linesListener != null) {
                linesListener.onRefreshLinesList(lineTable.getAllLines());
            }
        }

        else {
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    INIT_URL + "linha-faturas/" + userPreferences.getInt(LoginActivity.RESERVATION_ID, 0),
                    null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            System.out.println("--> " + response);

                            lines = jsonParser.new JsonLineParser().jsonLinesParser(response);
                            addLinesDB(lines);

                            if (linesListener != null) {
                                linesListener.onRefreshLinesList(lines);
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

    public void addLineAPI(final Invoice_line line, final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();
        }

        else {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    INIT_URL + "linha-faturas/createline",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if ((new JSONObject(response)).getBoolean("success")) {
                                    addLineDB(jsonParser.new JsonLineParser().jsonLineParser(response));
                                    Toast.makeText(context, "Linha inserida com sucesso!", Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                                }
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("--> Error adding Line on API " + error.getMessage());
                        }
                    }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("id", Integer.toString(line.getId()));
                    headers.put("quantidade", Integer.toString(line.getQty()));
                    headers.put("servico_id", line.getService() != null ? Integer.toString(line.getService().getId()) : "");
                    headers.put("refeicao_id", line.getFood() != null ? Integer.toString(line.getFood().getId()) : "");
                    headers.put("sub_total", Float.toString(line.getTotal()));
                    headers.put("preco_unitario", Float.toString(line.getUnit_price()));
                    headers.put("reserva_id", Integer.toString(line.getReservation()));
                    headers.put("status", line.getStatus().getStatus());

                    return headers;

                }
            };

            volleyQueue.add(request);
        }
    }

    public void editQtyLineAPI(Invoice_line line, final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection!", Toast.LENGTH_SHORT).show();
        }

        else {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    INIT_URL + "linha-faturas/editqty/" + line.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            editLineDB(line);
                            Toast.makeText(context, "Linha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("--> Erro: " + error.getMessage());
                        }
                    }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("status", Integer.toString(line.getQty()));

                    return headers;
                }
            };

            volleyQueue.add(request);
        }
    }

    public void editStatusLineAPI(Invoice_line line, final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection!", Toast.LENGTH_SHORT).show();
        }

        else {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    INIT_URL + "linha-faturas/editstatus/" + line.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            editLineDB(line);
                            Toast.makeText(context, "Linha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("--> Erro: " + error.getMessage());
                        }
                    }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("status", Status.CONFIRMADO.getStatus());

                    return headers;
                }
            };

            volleyQueue.add(request);
        }
    }


    public void removeLineAPI(final Invoice_line line, final Context context){
        if (!jsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "No internet Connection!", LENGTH_SHORT).show();
        }

        else{
            StringRequest req = new StringRequest(
                    Request.Method.DELETE,
                    INIT_URL + "linha-faturas/deleteline/" + line.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            removeLineDB(line.getId());
                            Toast.makeText(context, "Linha removida com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("---> Error removing from API" + error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }

    private void addLinesDB(ArrayList<Invoice_line> lines) {
        lineTable.removeAllLinesDB();

        for (Invoice_line line : lines) {
            addLineDB(line);
        }
    }

    private void addLineDB(Invoice_line line) {
        lineTable.addLineDB(line);
    }

    private void editLineDB(Invoice_line line) {
        Invoice_line auxLine = getLine(line.getId());

        if (auxLine != null) {
            lineTable.editLineDB(auxLine);
        }
    }

    public void removeLineDB(int id){
        Invoice_line auxLine = getLine(id);
        if (auxLine != null)
            lineTable.removeLineDB(id);
    }

    public void addInvoiceAPI(final Invoice invoice, final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();
        }

        else {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    INIT_URL + "faturas/invoices",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if ((new JSONObject(response)).getBoolean("success")) {
                                    Toast.makeText(context, "Fatura criada com sucesso!", Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                                }
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("--> Error adding Line on API " + error.getMessage());
                        }
                    }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("id", Integer.toString(invoice.getId()));
                    headers.put("pousada_id", Integer.toString(invoice.getLodge()));
                    headers.put("data_pagamento", geral_.convertFromDate(invoice.getPayment_date()));
                    headers.put("reserva_id", Integer.toString(invoice.getReservation()));
                    headers.put("preco_total", Float.toString(invoice.getTotal_price()));

                    return headers;

                }
            };

            volleyQueue.add(request);
        }
    }

}
