package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pousadas.R;
import com.example.pousadas.databinding.FragmentServicesClientBinding;

public class ServicesClientFragment extends Fragment {

    /* Lista de Serviços */
    private ListView servicesList;

    private FragmentServicesClientBinding binding;


    public ServicesClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_client, container, false);

        binding = FragmentServicesClientBinding.inflate(getLayoutInflater());
        binding.getRoot();

        servicesList = binding.listFood;

        return view;
    }
}