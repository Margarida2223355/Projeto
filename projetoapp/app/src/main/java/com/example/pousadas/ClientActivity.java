package com.example.pousadas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        /* Desativar item do meio do menu */
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        /* Função para definir botões */
        defineButtons();

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
        menuButton = findViewById(R.id.menuButton);

        buttons.put("btnFood", findViewById(R.id.btnFood));
        buttons.put("btnRoom", findViewById(R.id.btnRoom));
        buttons.put("btnSettings", findViewById(R.id.btnSettings));
        buttons.put("btnShop", findViewById(R.id.btnShop));

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