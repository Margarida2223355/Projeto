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

import com.example.pousadas.fragments.RoomClientFragment;
import com.example.pousadas.models.Geral;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FuncActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private LinkedHashMap<String, FloatingActionButton> buttons = new LinkedHashMap<>(); //Botões Menu Funcionário
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        /* Desativar item do meio do menu */
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        /* Função para definir botões */
        defineButtons();

        // Classe com método comum às classes ClientActivity, AdminActivity e FuncActivity.
        Geral geral = new Geral(menuButton, getBaseContext());

        /* Ao clicar no botão do menu irão ser apresentados 4 floating buttons com as opções */
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Enviar lista de botões para a função de mostrar o menu */
                geral.toggleMenu(buttons);

                /* Substituir fragment */
                //fragmentManager = getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.fragmentClient, new RoomClientFragment()).commit();
            }
        });
    }

    private void defineButtons() {
        menuButton = findViewById(R.id.menuButton);

        buttons.put("btnRoom", findViewById(R.id.btnRoom));
        buttons.put("btnTasks", findViewById(R.id.btnTasks));
        buttons.put("btnSettings", findViewById(R.id.btnSettings));

        for (Map.Entry<String, FloatingActionButton> button : buttons.entrySet()) {
            button.getValue().setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRoom) {
            Toast.makeText(this, "Room", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnTasks) {
            Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show();
        }

        else if (v.getId() == R.id.btnSettings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
    }
}