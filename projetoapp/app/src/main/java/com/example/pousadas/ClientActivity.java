package com.example.pousadas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.pousadas.databinding.ActivityClientBinding;
import com.example.pousadas.databinding.AppbarHomeBinding;
import com.example.pousadas.models.Geral;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private LinkedHashMap<String, FloatingActionButton> buttons = new LinkedHashMap<>(); //Botões Menu Client
    private FragmentManager fragmentManager;
    private Geral geral_;
    private ActivityClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Função para definir botões e navbar */
        defineButtons();

        /* Desativar item do meio do menu */
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        // Classe com método comum às classes ClientActivity, AdminActivity e FuncActivity.
        geral_ = new Geral(menuButton, getBaseContext());

        /* Ao clicar no botão do menu irão ser apresentados 5 floating buttons com as opções */
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Substituir fragment */
                //fragmentManager = getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.fragmentClient, new RoomClientFragment()).commit();

                /* Enviar lista de botões para a função de mostrar o menu */
                geral_.toggleMenu(buttons);
            }
        });
    }

    private void defineButtons() {
        bottomNavigationView = binding.appbarHome.bottomNavView;

        menuButton = binding.appbarHome.menuButton;

        buttons.put("btnFood", binding.menuClient.btnFood);
        buttons.put("btnRoom", binding.menuClient.btnRoom);
        buttons.put("btnSettings", binding.menuClient.btnSettings);
        buttons.put("btnShop", binding.menuClient.btnShop);

        for (Map.Entry<String, FloatingActionButton> button : buttons.entrySet()) {
            button.getValue().setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnFood) {
            Toast.makeText(this, "Food", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnRoom) {
            Toast.makeText(this, "Room", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnSettings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnShop) {
            Toast.makeText(this, "Shop", Toast.LENGTH_SHORT).show();
        }
    }
}