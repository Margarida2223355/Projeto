package com.example.pousadas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.pousadas.enums.Schedule;
import com.example.pousadas.enums.Status;
import com.example.pousadas.models.Reservation;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Room;
import com.example.pousadas.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ReservationJsonParser {

    /* Método para obter lista de reservas:
     *
     * Recebe a resposta em formato JSON
     * Converter para lista de reservas
     *
     */
    public static ArrayList<Reservation> jsonReservationsParser(JSONArray response) {
        Geral geral_ = new Geral();
        ArrayList<Reservation> reservations = new ArrayList<>();

        try {
            for (int i=0; i<response.length(); i++) {
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
                        Status.getFromString(reservation.getString("status")),
                        auxUser,
                        auxRoom
                );

                reservations.add(auxReservation);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return reservations;
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
