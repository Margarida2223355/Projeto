package com.example.pousadas.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ActivityAdminBinding;
import com.example.pousadas.fragments.FoodClientFragment;
import com.example.pousadas.fragments.ReservationClientFragment;
import com.example.pousadas.utils.Geral;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private LinkedHashMap<String, FloatingActionButton> buttons = new LinkedHashMap<>(); //Botões Menu Admin
    private FragmentManager fragmentManager;
    private Geral geral_;
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
        geral_= new Geral(menuButton, getBaseContext());

        /* Definir fragmento inicial - room */
        fragmentManager = getSupportFragmentManager();
        setFragmentByView(binding.menuAdmin.btnRoom);

        /* Ao clicar no botão do menu irão ser apresentados 5 floating buttons com as opções */
        menuButton.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(this, "Room", Toast.LENGTH_SHORT).show();
        }

        else if (view.getId() == R.id.btnReservation) {
            fragment = new FoodClientFragment();
            Toast.makeText(this, "Reservation", Toast.LENGTH_SHORT).show();
        }

        else if (view.getId() == R.id.btnUsers) {
            //fragment = new FoodClientFragment();
            Toast.makeText(this, "Users", Toast.LENGTH_SHORT).show();
        }

        else if (view.getId() == R.id.btnFinance) {
            //fragment = new FoodClientFragment();
            Toast.makeText(this, "Finance", Toast.LENGTH_SHORT).show();
        }

        else if (view.getId() == R.id.btnSettings) {
            //fragment = new FoodClientFragment();
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentClient,fragment).commit();
        }
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