package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;

public class FoodClientFragment extends Fragment {

    /* Lista Food */
   // private ListView listFood;
    private ArrayList<Food> foods;
    private FragmentFoodClientBinding binding;
    private Geral geral_ = new Geral();

    public FoodClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        /* Ao clicar no text field, abre o date picker */
        binding.txtFoodDate.setOnClickListener(new View.OnClickListener() {
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

        /* Ir buscar lista de refeições criada na classe Singleton */
        foods = Singleton.getInstance().getFoods();

        /* Enviar lista para o adaptador */
        binding.listFood.setAdapter(new ListFoodAdapter(getContext(), foods));

        return view;
    }
}