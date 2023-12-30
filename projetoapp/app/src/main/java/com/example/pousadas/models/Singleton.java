package com.example.pousadas.models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pousadas.db.DBHelper;
import com.example.pousadas.enums.Schedule;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.listeners.ReservationsListener;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.utils.FoodJsonParser;
import com.example.pousadas.utils.ReservationJsonParser;
import com.example.pousadas.utils.ServiceJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class Singleton {
    private Geral geral_ = new Geral();
    private ArrayList<Food> foods;
    private ArrayList<Service> services;
    private ArrayList<Reservation> reservations;
    private static Singleton instance;
    private FoodsListener foodsListener;
    private ServicesListener servicesListener;
    private ReservationsListener reservationsListener;
    private DBHelper dbHelper;
    private DBHelper.FoodsTable foodsTable;
    private DBHelper.ServicesTable servicesTable;
    private DBHelper.ReservationsTable reservationsTable;
    private static RequestQueue volleyQueue;
    private static String apiUrl;

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

        if (!FoodJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (foodsListener != null) {
                foodsListener.onRefreshFoodsList(foodsTable.getAllFoods());
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
        foodsTable.removeAllFoodsDB();

        for (Food food : foods) {
            addFoodDB(food);
        }
    }

    private void addFoodDB(Food food) {
        foodsTable.addFoodDB(food);
    }

    public void getAllServices(final Context context) {
        if (!FoodJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (servicesListener != null) {
                servicesListener.onRefreshServicesList(servicesTable.getAllServices());
            }
        }

        else {
            apiUrl = "http://192.168.1.91/Projeto/projeto/backend/web/api/servicos";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("--> " + response);

                    services = ServiceJsonParser.jsonServicesParser(response);
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
        if (!ReservationJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();

            if (reservationsListener != null) {
                reservationsListener.onRefreshReservationsList(reservationsTable.getAllReservations());
            }
        }

        else {
            apiUrl = "http://192.168.1.91/Projeto/projeto/backend/web/api/reservas/" + geral_.convertFromDate(geral_.getFromDate(initDate)) + "/" + geral_.convertFromDate(geral_.getFromDate(endDate));
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("--> " + response);

                    reservations = ReservationJsonParser.jsonReservationsParser(response);
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

}
