package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ActivityClientBinding;
import com.example.pousadas.fragments.FoodClientFragment;
import com.example.pousadas.fragments.ReservationClientFragment;
import com.example.pousadas.fragments.ServicesClientFragment;
import com.example.pousadas.fragments.ShopClientFragment;
import com.example.pousadas.models.Geral;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private LinkedHashMap<String, FloatingActionButton> buttons = new LinkedHashMap<>(); //Botões Menu Client
    private FragmentManager fragmentManager;
    private Geral geral_;
    private ActivityClientBinding binding;
    private SharedPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreferences = getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);

        /* Função para definir botões e navbar */
        defineButtons();

        /* Desativar item do meio do menu */
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        // Classe com método comum às classes ClientActivity, AdminActivity e FuncActivity.
        geral_ = new Geral(menuButton, getBaseContext());

        /* Definir fragmento inicial - room */
        fragmentManager = getSupportFragmentManager();
        setFragmentByView(binding.menuClient.btnRoom);

        /* Ao clicar no botão do menu irão ser apresentados 5 floating buttons com as opções */
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Enviar lista de botões para a função de mostrar o menu */
                geral_.toggleMenu(buttons);

            }
        });
    }

    /* Método que recebe o botão e abre o fragmento consoante o botão selecionado */
    private void setFragmentByView(View view) {
        Fragment fragment = null;

        if (view.getId() == R.id.btnRoom) {
            fragment = new ReservationClientFragment();
        }

        else if (view.getId() == R.id.btnFood) {
            fragment = new FoodClientFragment();
        }

        else if (view.getId() == R.id.btnServices) {
            fragment = new ServicesClientFragment();
        }

        else if (view.getId() == R.id.btnShop) {
            fragment = new ShopClientFragment();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentClient,fragment).commit();
        }
    }

    private void defineButtons() {
        bottomNavigationView = binding.appbarHome.bottomNavView;

        menuButton = binding.appbarHome.menuButton;

        buttons.put("btnFood", binding.menuClient.btnFood);
        buttons.put("btnRoom", binding.menuClient.btnRoom);
        buttons.put("btnServices", binding.menuClient.btnServices);
        buttons.put("btnShop", binding.menuClient.btnShop);

        for (Map.Entry<String, FloatingActionButton> button : buttons.entrySet()) {
            button.getValue().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragmentByView(v);
                    geral_.toggleMenu(buttons);
                }
            });
        }
    }
}