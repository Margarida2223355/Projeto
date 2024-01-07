package com.example.pousadas.listeners;

import com.example.pousadas.models.Reservation;

import java.util.ArrayList;

public interface ReservationsListener {
    void onRefreshReservationsList(ArrayList<Reservation> reservations);

    void onRefreshReservationActive(Reservation reservation);
}
