package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pousadas.databinding.ActivityLoginBinding;
import com.example.pousadas.listeners.UserListener;
import com.example.pousadas.models.Singleton;
import com.example.pousadas.models.User;

public class LoginActivity extends AppCompatActivity implements UserListener {

    private User loggedUser = null;
    private ActivityLoginBinding binding;
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
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
    }

    @Override
    public void onLogin(User user) {
        if (user != null) {

            getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putInt(USER_ID, user.getId())
                    .putString(USER_NAME, user.getNome())
                    .apply();

            startActivity(new Intent(getApplicationContext(), IPConfigActivity.class));
            finish();
        }
    }
}

