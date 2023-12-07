package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.adapters.ListServiceAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.databinding.FragmentServicesClientBinding;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.Singleton;

import java.util.ArrayList;

public class ServicesClientFragment extends Fragment {

    /* Lista Food */
    // private ListView listServices;
    private ArrayList<Service> services;

    private FragmentServicesClientBinding binding;

    public ServicesClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServicesClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        /* Ir buscar lista de serviços criada na classe Singleton */
        services = Singleton.getInstance().getServices();

        /* Enviar lista para o adaptador */
        binding.listService.setAdapter(new ListServiceAdapter(getContext(), services));

        return view;
    }
}