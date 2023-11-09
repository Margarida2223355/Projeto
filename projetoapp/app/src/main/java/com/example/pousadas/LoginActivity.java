package com.example.pousadas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        login_aut();
    }

    private void login_aut() {

        txtUsername.setText("Admin");
        txtPassword.setText("123456789");
    }

    private boolean checkAuth(String email, String pass) {
        return ((email == null) || (pass == null));
    }

    public void onClickLogin(View view) {
        if(checkAuth(txtUsername.getText().toString(),txtPassword.getText().toString())) {
            return;
        }

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}