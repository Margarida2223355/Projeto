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

public class ClientActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private LinkedHashMap<String, FloatingActionButton> buttons = new LinkedHashMap<>(); //Botões Menu Client
    private FragmentManager fragmentManager;

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
        Geral geral = new Geral(menuButton, getBaseContext());

        /* Ao clicar no botão do menu irão ser apresentados 5 floating buttons com as opções */
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Substituir fragment */
                //fragmentManager = getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.fragmentClient, new RoomClientFragment()).commit();

                /* Enviar lista de botões para a função de mostrar o menu */
                geral.toggleMenu(buttons);

                FloatingActionButton btn = findViewById(R.id.btnRoom);
                btn.setVisibility(View.VISIBLE);
                btn.setY(menuButton.getY() - 500);
                btn.setClickable(true);
            }

        });

        FloatingActionButton btn = findViewById(R.id.btnRoom);
        btn.setVisibility(View.VISIBLE);
        btn.setY(menuButton.getY() - 500);
        btn.setClickable(true);

    }

    private void defineButtons() {
        menuButton = findViewById(R.id.menuButton);

        buttons.put("btnFood", findViewById(R.id.btnFood));
        buttons.put("btnRoom", findViewById(R.id.btnRoom));
        buttons.put("btnSettings", findViewById(R.id.btnSettings));
        buttons.put("btnShop", findViewById(R.id.btnShop));

        for (Map.Entry<String, FloatingActionButton> button : buttons.entrySet()) {
            button.getValue().setOnClickListener(clickListener);
        }
    }

    private OnClickListener clickListener = v -> {
        Toast.makeText(ClientActivity.this, "jhgjyg", Toast.LENGTH_SHORT).show();
        if (v.getId() == R.id.btnFood) {
            Toast.makeText(ClientActivity.this, "Room", Toast.LENGTH_SHORT).show();
        }
    };
}