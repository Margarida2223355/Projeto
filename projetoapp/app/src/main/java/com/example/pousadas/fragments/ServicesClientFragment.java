package com.example.pousadas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pousadas.activities.LoginActivity;
import com.example.pousadas.adapters.ListServiceAdapter;
import com.example.pousadas.databinding.FragmentServicesClientBinding;
import com.example.pousadas.enums.Status;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.Singleton;

import java.util.ArrayList;

public class ServicesClientFragment extends Fragment implements ServicesListener {

    private FragmentServicesClientBinding binding;
    private SharedPreferences userPreferences;

    public ServicesClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServicesClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userPreferences = getContext().getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);

        Singleton.getInstance(getContext()).setServicesListener(this);
        Singleton.getInstance(getContext()).getAllServices(getContext());

        /* Método onclicklistener do botão adicionar:
         *
         * Para adicionar os serviços ao carrinho.
         */
        binding.add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Service> addServices = new ArrayList<>();

                for (int i = 0; i < binding.listService.getAdapter().getCount(); i++) {
                    Service service = (Service) binding.listService.getAdapter().getItem(i);
                    if (service.getQty() > 0) {
                        addServices.add(service);
                    }
                }

                for (Service service : addServices) {
                    Singleton.getInstance(getContext()).addLineAPI(new Invoice_line(
                            0,
                            service.getQty(),
                            service,
                            null,
                            service.getTotal(),
                            service.getPrice(),
                            userPreferences.getInt(LoginActivity.RESERVATION_ID, 0),
                            Status.CARRINHO
                    ), getContext());
                }
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