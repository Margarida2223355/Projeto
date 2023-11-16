package com.example.pousadas;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton menuButton;
    private boolean menuOpen = false; //Menu começa fechado
    private FloatingActionButton btnRoom, btnFood, btnExtra, btnSettings, btnHelp; //Botões Menu Client
    private View.OnClickListener listener;

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
                btnSettings = findViewById(R.id.btnSettings);
                btnHelp = findViewById(R.id.btnHelp);

                /* Enviar lista de botões para a função de mostrar o menu */
                toogleMenu(new ArrayList<FloatingActionButton>(
                        //Arrays.asList(new FloatingActionButton[]{btnRoom, btnFood, btnExtra, btnSettings, btnHelp})
                        Arrays.asList(new FloatingActionButton[]{btnRoom, btnFood, btnExtra, btnSettings, btnHelp})
                ));
            }

            /* Método para abrir ou fechar o menu */
            private void toogleMenu(ArrayList<FloatingActionButton> buttons) {
                //Definir coordenada do botão Menu
                float y = menuButton.getY();

                //Definir raio de círculo onde ficarão os botões
                float r = 1.5F * menuButton.getWidth();

                //Se o menu estiver fechado
                if (!menuOpen) {
                    int i = 0;

                    for (FloatingActionButton btn : buttons) {
                        //Definir ângulo inicial - 180/nº de botões
                        float angle = (float) (PI / (buttons.size() + 1)) * (i + 1);

                        /* Definir posição inicial de cada botão com base na posição do botão Menu
                         * Apenas alterou-se no Y pois os botões estão abaixo do centro do botão Menu
                         */
                        btn.setY(y);

                        /* Criar animação do tipo Translate:
                         *
                         * RELATIVE_TO_SELF - valor é multiplicado pelo Width/Height do objeto
                         * ABSOLUTE - valor absoluto em pixels
                         *
                         * fromXValue / fromYValue - ponto inicial em x / y
                         * toXValue / toYValue - ponto final em x / y
                         */
                        TranslateAnimation move = new TranslateAnimation(
                                Animation.ABSOLUTE, 0.0F,
                                Animation.ABSOLUTE, (float) -(r * cos(angle)),
                                Animation.ABSOLUTE, 0.0F,
                                Animation.ABSOLUTE, (float) -(r * sin(angle))
                        );

                        //Definir duração da animação - 1s
                        move.setDuration(1000);

                        AnimationSet animation = new AnimationSet(false);

                        animation.addAnimation(move);
                        animation.addAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fab1_show));

                        //Para que a transformação da animação se mantenha após esta terminar
                        animation.setFillAfter(true);

                        //Colocar o botão visível e habilitar a opção de clicar no mesmo
                        btn.setVisibility(View.VISIBLE);
                        btn.setClickable(true);

                        //Iniciar animação
                        btn.startAnimation(animation);

                        i++;
                    }
                } else {
                    int i = 0;

                    for (FloatingActionButton btn : buttons) {
                        //Definir ângulo inicial - 180/nº de botões
                        float angle = (float) (PI / (buttons.size() + 1)) * (i + 1);

                        /* Criar animação do tipo Translate:
                         *
                         * RELATIVE_TO_SELF - valor é multiplicado pelo Width/Height do objeto
                         * ABSOLUTE - valor absoluto em pixels
                         *
                         * fromXValue / fromYValue - ponto inicial em x / y
                         * toXValue / toYValue - ponto final em x / y
                         */
                        TranslateAnimation move = new TranslateAnimation(
                                Animation.ABSOLUTE, (float) -(r * cos(angle)),
                                Animation.ABSOLUTE, 0.0F,
                                Animation.ABSOLUTE, (float) -(r * sin(angle)),
                                Animation.ABSOLUTE, 0.0F
                        );

                        //Definir duração da animação - 1s
                        move.setDuration(1000);

                        //Colocar o botão visível e habilitar a opção de clicar no mesmo
                        btn.setVisibility(View.INVISIBLE);
                        btn.setClickable(false);

                        //Iniciar animação
                        btn.startAnimation(move);

                        i++;
                    }
                }

                //Toogle menu status
                menuOpen = !menuOpen;
            }
        });

    }
}