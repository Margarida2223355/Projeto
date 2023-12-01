package com.example.pousadas.models;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.example.pousadas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* Classe para utilizar o mesmo método em várias classes do projeto */
public class Geral {
    /* Floating Menus */
    private FloatingActionButton menuButton;
    private Context baseContext;
    private boolean menuOpen;

    /* Floating Menus */
    public Geral(FloatingActionButton menuButton, Context baseContext) {
        this.menuButton = menuButton;
        this.baseContext = baseContext;
        menuOpen = false;
    }

    /* Floating Menus */
    // Método para abrir floating menus nas activities
    public void toggleMenu(LinkedHashMap<String, FloatingActionButton> buttons) {
        //Definir coordenada do botão Menu
        float y = menuButton.getY();

        //Definir raio de círculo onde ficarão os botões
        float r = 1.5F * menuButton.getWidth();

        //Se o menu estiver fechado
        if (!menuOpen) {
            int i = 0;

            for (Map.Entry<String, FloatingActionButton> btn : buttons.entrySet()) {
                //Definir ângulo inicial - 180/nº de botões
                float angle = (float) (PI / (buttons.size() + 1)) * (i + 1);

                /* Definir posição inicial de cada botão com base na posição do botão Menu
                 * Apenas alterou-se no Y pois os botões estão abaixo do centro do botão Menu
                 */
                btn.getValue().setY(y);

                /* Criar animação do tipo Translate:
                 *
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
                animation.addAnimation(AnimationUtils.loadAnimation(baseContext, R.anim.fab1_show));

                //Iniciar animação
                btn.getValue().startAnimation(animation);

                //Colocar o botão visível e habilitar a opção de clicar no mesmo
                //btn.getValue().setVisibility(View.VISIBLE);
                btn.getValue().setClickable(true);

                //Para que a transformação da animação se mantenha após esta terminar
                animation.setFillAfter(true);

                i++;
            }
        }

        else {
            int i = 0;

            for (Map.Entry<String, FloatingActionButton> btn : buttons.entrySet()) {
                //Definir ângulo inicial - 180/nº de botões
                float angle = (float) (PI / (buttons.size() + 1)) * (i + 1);

                /* Criar animação do tipo Translate:
                 *
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
                btn.getValue().setVisibility(View.INVISIBLE);
                btn.getValue().setClickable(false);

                //Iniciar animação
                btn.getValue().startAnimation(move);

                i++;
            }
        }
        //Toogle menu status
        menuOpen = !menuOpen;
    }
}
