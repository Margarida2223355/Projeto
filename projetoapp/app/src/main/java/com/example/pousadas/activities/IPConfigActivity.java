package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ActivityIpconfigBinding;

public class IPConfigActivity extends AppCompatActivity {

    private ActivityIpconfigBinding binding;
    public static final String IP = "IP";
    public static final String IPCONFIG = "IP Config";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipconfig);

        binding = ActivityIpconfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* *************************************** */
        binding.txtIP.setText("192.168.1.184");
        /* *************************************** */

        getSharedPreferences(IPCONFIG, Context.MODE_PRIVATE)
                .edit()
                .putString(IP, binding.txtIP.getText().toString())
                .apply();

        /* Atualizar txtField do IP
         *
         * Utilizador apenas terá de inserir os números,
         * os pontos aparecerão automaticamente.
         * Para evitar erros de sintaxe.
         */
        binding.txtIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().replaceAll("[^\\d.]", ""); // Remover caracteres não numéricos e não pontos

                StringBuilder formattedIp = new StringBuilder();

                /* Grupo de 3 números
                 *
                 * ___.
                 * Exemplo: 192.
                 */
                for (int i = 0; i < Math.min(input.length(), 3); i++) {
                    formattedIp.append(input.charAt(i));
                }

                if (formattedIp.length() == 3) {
                    formattedIp.append('.');
                }

                /* Grupo de 3 números entre dois pontos
                 *
                 * ___.___.
                 * Exemplo: 192.168.
                 */

                for (int i = 4; i < Math.min(input.length(), 7); i++) {
                    formattedIp.append(input.charAt(i));
                }

                if (formattedIp.length() == 7) {
                    formattedIp.append('.');
                }

                /* Grupo de 1 número entre dois pontos
                 *
                 * ___.___._.
                 * Exemplo: 192.168.1.
                 */
                for (int i = 8; i < Math.min(input.length(), 9); i++) {
                    formattedIp.append(input.charAt(i));
                }

                if (formattedIp.length() == 9) {
                    formattedIp.append('.');
                }

                /* Restantes dois números
                 *
                 * ___.___._.__
                 * Exemplo: 192.168.1.92
                 */
                for (int i = 10; i < Math.min(input.length(), 12); i++) {
                    formattedIp.append(input.charAt(i));

                    if (i == 12) {
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }

                binding.txtIP.removeTextChangedListener(this); // Remover o TextWatcher temporariamente para evitar chamadas recursivas
                binding.txtIP.setText(formattedIp.toString());
                binding.txtIP.setSelection(formattedIp.length());
                binding.txtIP.addTextChangedListener(this);
            }
        });

        /* Ao clicar no botão Star abrir a Activity */
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}