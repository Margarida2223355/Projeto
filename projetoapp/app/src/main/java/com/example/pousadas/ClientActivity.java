package com.example.pousadas;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.example.pousadas.models.Geral;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private FloatingActionButton btnRoom, btnFood, btnShop, btnSettings; //Botões Menu Client
    private View.OnClickListener listener;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        /* Desativar item do meio do menu */
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        menuButton = findViewById(R.id.menuButton);

        // Classe com método comum às classes ClientActivity, AdminActivity e FuncActivity.
        Geral geral = new Geral(menuButton, getBaseContext());

        /* Ao clicar no botão do menu irão ser apresentados 5 floating buttons com as opções */
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRoom = findViewById(R.id.btnRoom);
                btnFood = findViewById(R.id.btnFood);
                btnShop = findViewById(R.id.btnShop);
                btnSettings = findViewById(R.id.btnSettings);

                /* Enviar lista de botões para a função de mostrar o menu */
                geral.toogleMenu(new ArrayList<FloatingActionButton>(
                        //Arrays.asList(new FloatingActionButton[]{btnRoom, btnFood, btnExtra, btnSettings, btnHelp})
                        Arrays.asList(new FloatingActionButton[]{btnRoom, btnFood, btnShop, btnSettings})
                ));
            }
        });

        /* Substituir fragment */
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentClient, new RoomClientFragment()).commit();
    }
}