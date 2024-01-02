package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pousadas.databinding.ActivityLoginBinding;
import com.example.pousadas.enums.Role;
import com.example.pousadas.models.User;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private User user = new User();
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        login_aut();
    }

    private void login_aut() {

        binding.txtUsername.setText("Admin");
        binding.txtPassword.setText("123456789");

    }

    private boolean checkAuth(String email, String pass) {
        return ((email == null) || (pass == null));
    }

    public void onClickLogin(View view) {
        if(checkAuth(binding.txtUsername.getText().toString(),binding.txtPassword.getText().toString())) {
            return;
        }

        /* Abrir atividade consoante o role do user que fez login */
        user.setRole(Role.CLIENT); //Para teste
        switch (user.getRole()) {
            case ADMIN:
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                break;
            case FUNC:
                startActivity(new Intent(getApplicationContext(), FuncActivity.class));
                break;
            case CLIENT:
                //startActivity(new Intent(getApplicationContext(), ClientActivity.class));
                startActivity((new Intent(getApplicationContext(), IPConfigActivity.class)).putExtra(IPConfigActivity.NAME, binding.txtUsername.getText().toString()));
                break;
        }
        finish();
    }
}

