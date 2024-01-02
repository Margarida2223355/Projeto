package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ActivityReservationDetailBinding;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Reservation;
import com.example.pousadas.models.Singleton;

import java.util.Locale;

public class ReservationDetailActivity extends AppCompatActivity {

    public static final String ID_RESERVATION = "ID_RESERVATION";
    private Reservation reservation;
    private ActivityReservationDetailBinding binding;
    private Geral geral_ = new Geral();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

        binding = ActivityReservationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reservation = Singleton.getInstance(getApplicationContext()).getReservation(getIntent().getIntExtra(ID_RESERVATION, 0));

        if (reservation != null) {
            loadReservation();
        }

    }

    private void loadReservation() {
        binding.eClientReservation.setText(String.valueOf(reservation.getId()));
        binding.eClientRoom.setText(reservation.getRoom().getDescricao());
        binding.eClientInit.setText(geral_.convertFromDateTxt(reservation.getInit_date()));
        binding.eClientEnd.setText(geral_.convertFromDateTxt(reservation.getEnd_date()));
        binding.eClientTotal.setText(String.format(Locale.FRANCE, "%.2f", reservation.getTotal_price()));
        binding.eClientStatus.setText(reservation.getStatus().toString());
    }
}