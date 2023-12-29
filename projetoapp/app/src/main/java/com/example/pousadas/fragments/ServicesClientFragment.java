package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListFoodAdapter;
import com.example.pousadas.adapters.ListServiceAdapter;
import com.example.pousadas.databinding.FragmentFoodClientBinding;
import com.example.pousadas.databinding.FragmentServicesClientBinding;
import com.example.pousadas.enums.Schedule;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Date;

public class ServicesClientFragment extends Fragment implements ServicesListener {

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

        Singleton.getInstance(getContext()).setServicesListener(this);
        Singleton.getInstance(getContext()).getAllServices(getContext());

        /* Método onclicklistener do botão adicionar:
         *
         * Para adicionar os serviços ao carrinho.
         */
        binding.add.setOnClickListener(new View.OnClickListener() {
            ArrayList<Service> addServices;
            @Override
            public void onClick(View v) {
                addServices = new ArrayList<>();

                for (int i=0; i<binding.listService.getAdapter().getCount(); i++) {
                    Service service = (Service) binding.listService.getAdapter().getItem(i);
                    if (service.getQty() > 0) {
                        addServices.add(service);
                    }
                }
                Toast.makeText(getContext(), "Adicionar " + addServices.size(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onRefreshServicesList(ArrayList<Service> services) {
        if (services != null) {
            binding.listService.setAdapter(new ListServiceAdapter(getContext(), services));
        }
    }
}