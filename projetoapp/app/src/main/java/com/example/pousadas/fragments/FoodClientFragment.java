package com.example.pousadas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.activities.LoginActivity;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.enums.Category;
import com.example.pousadas.enums.Status;
import com.example.pousadas.listeners.FoodsListener;
import com.example.pousadas.models.Food;
import com.example.pousadas.utils.Geral;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class FoodClientFragment extends Fragment implements FoodsListener {

    private FragmentFoodClientBinding binding;
    private final Geral geral_ = new Geral();
    private MaterialAlertDialogBuilder alert;
    private SharedPreferences userPreferences;

    public FoodClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userPreferences = getContext().getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);

        /* *************************************** */
        binding.txtFoodDate.setText("2023-12-31");
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
                /* Condição para aparecer datas posteriores à atual */
                CalendarConstraints dateConstraint = new CalendarConstraints.Builder().setStart(MaterialDatePicker.todayInUtcMilliseconds()).build();

                /* Criar o datepicker*/
                MaterialDatePicker picker = MaterialDatePicker.Builder
                    .datePicker()
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(dateConstraint)
                    .build();

                /* Mostrar o calendário */
                picker.show(getActivity().getSupportFragmentManager(), "tag");

                /* Ao clicar no button OK, converter a data selecionada em String e colocar no text field */
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                   @Override
                   public void onPositiveButtonClick(Object selection) {
                       binding.txtFoodDate.setText(geral_.convertFromDateTxt(picker.getSelection()));
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
                    Singleton.getInstance(getContext()).getFoodsByDateSchedule(geral_.convertToDate(binding.txtFoodDate.getText().toString()), Category.getFromString(binding.txtFoodTime.getText().toString()),getContext());
                }
            }
        });

        /* Método onclicklistener do botão adicionar:
         *
         * Para adicionar as refeições ao carrinho.
         */
        binding.add.setOnClickListener(new View.OnClickListener() {
            //ArrayList<Food> addFoods;
            @Override
            public void onClick(View v) {
                ArrayList<Food> addFoods = new ArrayList<>();

                for (int i = 0; i < binding.listFood.getAdapter().getCount(); i++) {
                    Food food = (Food) binding.listFood.getAdapter().getItem(i);
                    if (food.getQty() > 0) {
                        addFoods.add(food);
                    }
                }

                for (Food food : addFoods) {
                    System.out.println("--> " + food.getName());
                    Singleton.getInstance(getContext()).addLineAPI(new Invoice_line(
                            0,
                            food.getQty(),
                            null,
                            food,
                            food.getTotal(),
                            food.getPrice(),
                            userPreferences.getInt(LoginActivity.RESERVATION_ID, 0),
                            Status.CARRINHO
                    ), getContext());

                    food.resetQty();
                }

                Singleton.getInstance(getContext()).getFoodsByDateSchedule(geral_.convertToDate(binding.txtFoodDate.getText().toString()), Category.getFromString(binding.txtFoodTime.getText().toString()),getContext());
            }
        });

        return view;
    }

    @Override
    public void onRefreshFoodsList(ArrayList<Food> foods) {
        if (!foods.isEmpty()) {
            binding.listFood.setAdapter(new ListFoodAdapter(getContext(), foods));
        }

        else {
            Toast.makeText(requireContext(), "Não existem refeições nestas condições!", Toast.LENGTH_SHORT).show();
        }
    }

}