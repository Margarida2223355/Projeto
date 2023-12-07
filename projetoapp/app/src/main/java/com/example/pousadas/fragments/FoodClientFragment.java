package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.SingletonFood;

import java.util.ArrayList;

public class FoodClientFragment extends Fragment {

    /* Lista Food */
   // private ListView listFood;
    private ArrayList<Food> foods;

    private FragmentFoodClientBinding binding;

    public FoodClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        /* Ir buscar lista de refeições criada na classe Singleton */
        foods = SingletonFood.getInstance().getFoods();

        /* Enviar lista para o adaptador */
        binding.listFood.setAdapter(new ListFoodAdapter(getContext(), foods));

        return view;
    }
}