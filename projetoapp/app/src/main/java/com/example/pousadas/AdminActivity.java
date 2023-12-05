package com.example.pousadas;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pousadas.databinding.ActivityAdminBinding;
import com.example.pousadas.fragments.RoomClientFragment;
import com.example.pousadas.models.Geral;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private LinkedHashMap<String, FloatingActionButton> buttons = new LinkedHashMap<>(); //Botões Menu Admin
    private FragmentManager fragmentManager;
    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Função para definir botões e navbar */
        defineButtons();

        /* Desativar item do meio do menu */
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        // Classe com método comum às classes ClientActivity, AdminActivity e FuncActivity.
        Geral geral = new Geral(menuButton, getBaseContext());

        /* Ao clicar no botão do menu irão ser apresentados 5 floating buttons com as opções */
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Enviar lista de botões para a função de mostrar o menu */
                //geral.toggleMenu(buttons);

                /* Substituir fragment */
                //fragmentManager = getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.fragmentClient, new RoomClientFragment()).commit();
            }
        });
    }

    private void defineButtons() {

        bottomNavigationView = binding.appbarHome.bottomNavView;

        menuButton = binding.appbarHome.menuButton;

        buttons.put("btnRoom", binding.menuAdmin.btnRoom);
        buttons.put("btnReservation", binding.menuAdmin.btnReservation);
        buttons.put("btnUsers", binding.menuAdmin.btnUsers);
        buttons.put("btnFinance", binding.menuAdmin.btnFinance);
        buttons.put("btnSettings", binding.menuAdmin.btnSettings);

        for (Map.Entry<String, FloatingActionButton> button : buttons.entrySet()) {
            button.getValue().setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRoom) {
            Toast.makeText(this, "Room", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnReservation) {
            Toast.makeText(this, "Reservation", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnUsers) {
            Toast.makeText(this, "Users", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnFinance) {
            Toast.makeText(this, "Finance", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnSettings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
    }
}