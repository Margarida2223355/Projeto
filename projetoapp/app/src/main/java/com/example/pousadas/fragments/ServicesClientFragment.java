package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.adapters.ListServiceAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.databinding.FragmentServicesClientBinding;
import com.example.pousadas.enums.Schedule;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Date;

public class ServicesClientFragment extends Fragment {

    private ArrayList<Service> services;
    private FragmentServicesClientBinding binding;
    private final Geral geral_ = new Geral();
    private MaterialAlertDialogBuilder alert;

    public ServicesClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServicesClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        services = Singleton.getInstance().getServices();

        /* Enviar lista para o adaptador */
        binding.listService.setAdapter(new ListServiceAdapter(getContext(), services));

        return view;
    }
}