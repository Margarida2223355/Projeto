package com.example.pousadas.fragments;

import static com.example.pousadas.R.layout.fragment_food_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;

public class FoodClientFragment extends Fragment {

    /* Lista Food */
   // private ListView listFood;
    private ArrayList<Food> foods;
    private FragmentFoodClientBinding binding;
    private Geral geral_ = new Geral();
    private MaterialAlertDialogBuilder alert;

    public FoodClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        /* Criar a mensagem de alert */
        alert = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Erro")
                .setPositiveButton("OK", null);

        /* Ao clicar no text field, abre o date picker */
        binding.calendar.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Criar o datepicker*/
                MaterialDatePicker picker = MaterialDatePicker.Builder
                    .datePicker()
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

                /* Mostrar o calendário */
                picker.show(getActivity().getSupportFragmentManager(), "tag");

                /* Ao clicar no button OK, converter a data selecionada em String e colocar no text field */
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                   @Override
                   public void onPositiveButtonClick(Object selection) {
                       binding.txtFoodDate.setText(geral_.getDate(picker.getSelection()));
                   }
                });
            }
        });

        /* Dropdown de horário - Almoço ou Jantar */
        binding.txtFoodTime.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.dropdown_item, new String[] {"Almoço", "Jantar"}));

        /* Método quando se clica na lupa para pesquisar as refeições disponíveis */
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "-->" + binding.txtFoodDate.getText().toString());

                /* Verificar se a data está preenchida */
                if (TextUtils.isEmpty(binding.txtFoodDate.getText())) {
                    alert.setMessage("Falta inserir data")
                            .create()
                            .show();

                    return;
                }

                /* Verificar se o horário foi selecionado */
                if (binding.txtFoodTime.getText() == null) {
                    alert.setMessage("Falta escolher o horário")
                            .show();
                }

                else {
                    /* Ir buscar lista de refeições criada na classe Singleton */
                    foods = Singleton.getInstance().getFoods();

                    /* Enviar lista para o adaptador */
                    binding.listFood.setAdapter(new ListFoodAdapter(getContext(), foods));
                }
            }
        });

        return view;
    }
}