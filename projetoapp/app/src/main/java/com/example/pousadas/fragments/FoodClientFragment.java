package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.enums.Schedule;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Date;

public class FoodClientFragment extends Fragment implements FoodsListener {

    private ArrayList<Food> foods;
    private FragmentFoodClientBinding binding;
    private final Geral geral_ = new Geral();
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

        /* *************************************** */
        binding.txtFoodDate.setText(geral_.convertFromDate(new Date()));
        binding.txtFoodTime.setText("Jantar");
        /* *************************************** */

        /* Criar a mensagem de alert */
        alert = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Erro")
                .setPositiveButton("OK", null);

        Singleton.getInstance(getContext()).setFoodsListener(this);

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
                       binding.txtFoodDate.setText(geral_.convertFromDate(picker.getSelection()));
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
                //Log.i("TAG", "-->" + binding.txtFoodDate.getText().toString());

                /* Verificar se a data e horário estão preenchidos */
                if (TextUtils.isEmpty(binding.txtFoodDate.getText())
                    ||
                    TextUtils.isEmpty(binding.txtFoodTime.getText())) {

                    alert.setMessage("Falta inserir data/horário")
                            .create()
                            .show();

                }

                else {
                    Singleton.getInstance(getContext()).getAllFoodsAPI(getContext());

                    /* Enviar lista para o adaptador */
                    //binding.listFood.setAdapter(new ListFoodAdapter(getContext(), foods));
                }
            }
        });

        /* Método onclicklistener do botão adicionar:
         *
         * Para adicionar as refeições ao carrinho.
         */
        binding.add.setOnClickListener(new View.OnClickListener() {
            ArrayList<Food> addFoods;
            @Override
            public void onClick(View v) {
                addFoods = new ArrayList<>();

                for (int i=0; i<binding.listFood.getAdapter().getCount(); i++) {
                    Food food = (Food) binding.listFood.getAdapter().getItem(i);
                    if (food.getQty() > 0) {
                        addFoods.add(food);
                    }
                }
                Toast.makeText(getContext(), "Adicionar " + addFoods.size(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onRefreshFoodsList(ArrayList<Food> foods) {
        if (foods != null) {
            binding.listFood.setAdapter(new ListFoodAdapter(getContext(), foods));
        }
    }
}