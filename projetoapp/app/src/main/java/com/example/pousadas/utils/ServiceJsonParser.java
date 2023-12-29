package com.example.pousadas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pousadas.enums.Schedule;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.Geral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ServiceJsonParser {

    /* Método para obter lista de serviços:
     *
     * Recebe a resposta em formato JSON
     * Converter para lista de serviços
     *
     */
    public static ArrayList<Service> jsonServicesParser(JSONArray response) {
        Geral geral_ = new Geral();
        ArrayList<Service> services = new ArrayList<>();

        try {
            for (int i=0; i<response.length(); i++) {
                JSONObject service = (JSONObject) response.get(i);

                int idService = service.getInt("id");
                String nameService = service.getString("nome");
                String descriptionService = service.getString("descricao");
                float priceService = (float) service.getDouble("preco");

                Service auxService = new Service(idService, nameService, descriptionService, priceService);
                services.add(auxService);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return services;
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
