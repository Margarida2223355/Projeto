package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pousadas.databinding.ActivityLoginBinding;
import com.example.pousadas.listeners.ReservationsListener;
import com.example.pousadas.listeners.UserListener;
import com.example.pousadas.models.Reservation;
import com.example.pousadas.models.Singleton;
import com.example.pousadas.models.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements UserListener, ReservationsListener {

    private User loggedUser = null;
    private ActivityLoginBinding binding;
    public static final String USER_ID = "USER_ID";
    public static final String RESERVATION_ID = "RESERVATION_ID";
    public static final String PREFERENCES = "My Preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void onClickLogin(View view) {
        binding.txtUsername.setText("123456789");
        binding.txtPassword.setText("123456789");

        Singleton.getInstance(getApplicationContext()).setUserListener(this);

        Singleton.getInstance(getApplicationContext()).getUser(
                binding.txtUsername.getText().toString(),
                binding.txtPassword.getText().toString(),
                getApplicationContext()
        );

        Singleton.getInstance(getApplicationContext()).setReservationsListener(this);
        Singleton.getInstance(getApplicationContext()).getReservationAtive(getApplicationContext());

    }

    @Override
    public void onLogin(User user) {
        if (user != null) {

            getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putInt(USER_ID, user.getId())
                    .apply();

            startActivity(new Intent(getApplicationContext(), ClientActivity.class));
            finish();
        }
    }

    @Override
    public void onRefreshReservationsList(ArrayList<Reservation> reservations) {

    }

    @Override
    public void onRefreshReservationActive(Reservation reservation) {
        getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putInt(RESERVATION_ID, reservation.getId())
                .apply();
    }
}

