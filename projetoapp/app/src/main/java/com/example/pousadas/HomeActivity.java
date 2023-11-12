package com.example.pousadas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private FloatingActionButton btnRoom, btnFood, btnExtra, btnSettings, btnHelp; //Botões Menu Client

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* Desativar item do meio do menu */
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        menuButton = findViewById(R.id.menuButton);

        /* Ao clicar no botão do menu irão ser apresentados 4 floating buttons com as opções */
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRoom = findViewById(R.id.btnRoom);
                btnFood = findViewById(R.id.btnFood);
                btnExtra = findViewById(R.id.btnExtra);
                //btnSettings = findViewById(R.id.btnSettings);
                //btnHelp = findViewById(R.id.btnHelp);

                /* Enviar lista de botões para a função de mostrar o menu */
                toogleMenu(new ArrayList<FloatingActionButton>(
                        //Arrays.asList(new FloatingActionButton[]{btnRoom, btnFood, btnExtra, btnSettings, btnHelp})
                        Arrays.asList(new FloatingActionButton[]{btnRoom, btnFood, btnExtra})
                ));
            }

            /* Método para abrir ou fechar o menu */
            private void toogleMenu(ArrayList<FloatingActionButton> buttons) {
                float x = 2.4F, y=-.4F;
                final float FIRST = 2.0F;

                if (!menuOpen) {
                    for (FloatingActionButton btn : buttons) {
                        x-=2.0F/FIRST;
                        y+=2.0F/FIRST;
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) btn.getLayoutParams();
                        layoutParams.rightMargin += btn.getWidth() * 1.7;
                        layoutParams.bottomMargin += btn.getHeight() * .9;
                        btn.setLayoutParams(layoutParams);

                        TranslateAnimation move = new TranslateAnimation(
                                Animation.ABSOLUTE, 0.0F, Animation.ABSOLUTE, x,
                                Animation.ABSOLUTE,0.0F, Animation.ABSOLUTE, y);
                        move.setDuration(1000);

                        btn.startAnimation(move);
                        btn.setClickable(true);
                    }
                }

                else {
                    for (FloatingActionButton btn : buttons) {
                        x-=2.0F/FIRST;
                        y+=2.0F/FIRST;
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) btn.getLayoutParams();
                        layoutParams.rightMargin += btn.getWidth() * 1.7;
                        layoutParams.bottomMargin += btn.getHeight() * .9;
                        btn.setLayoutParams(layoutParams);

                        TranslateAnimation move = new TranslateAnimation(
                                Animation.ABSOLUTE, 0.0F, Animation.ABSOLUTE, -x,
                                Animation.ABSOLUTE,0.0F, Animation.ABSOLUTE, -y);
                        move.setDuration(1000);

                        btn.startAnimation(move);
                        btn.setClickable(true);

                        return;
                    }
                }

                //Toogle menu status
                menuOpen = !menuOpen;
            }
        });
    }
}