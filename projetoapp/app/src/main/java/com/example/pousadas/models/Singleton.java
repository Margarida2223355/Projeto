package com.example.pousadas.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pousadas.db.DBHelper;
import com.example.pousadas.enums.Category;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.listeners.ReservationsListener;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.listeners.UserListener;
import com.example.pousadas.utils.JsonParser;

import org.json.JSONArray;
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
    private static Singleton instance;
    private FoodsListener foodsListener;
    private ServicesListener servicesListener;
    private ReservationsListener reservationsListener;
    private UserListener userListener;
    private DBHelper dbHelper;
    private DBHelper.FoodsTable foodsTable;
    private DBHelper.ServicesTable servicesTable;
    private DBHelper.ReservationsTable reservationsTable;
    private DBHelper.UserTable userTable;
    private JsonParser jsonParser = new JsonParser();
    private static RequestQueue volleyQueue;
    public static final String INIT_URL = "http://192.168.1.92/Projeto/projeto/backend/web/api/";

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
        dbHelper = new DBHelper(context);
        foodsTable = dbHelper.new FoodsTable();
        servicesTable = dbHelper.new ServicesTable();
        reservationsTable = dbHelper.new ReservationsTable();
        userTable = dbHelper.new UserTable();
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
                    INIT_URL + "reservas/" + geral_.convertFromDate(geral_.getFromDate(initDate)) + "/" + geral_.convertFromDate(geral_.getFromDate(endDate)),
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
}
