package com.example.pousadas.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.activities.LoginActivity;
import com.example.pousadas.adapters.ListLineAdapter;
import com.example.pousadas.databinding.FragmentServicesClientBinding;
import com.example.pousadas.databinding.FragmentShopClientBinding;
import com.example.pousadas.listeners.LinesListener;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Invoice;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Singleton;

import java.util.ArrayList;
import java.util.Date;

public class ShopClientFragment extends Fragment implements LinesListener {

    private FragmentShopClientBinding binding;
    private Geral geral_ = new Geral();
    private SharedPreferences userPreferences;

    public ShopClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userPreferences = getContext().getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);

        Singleton.getInstance(getContext()).setLinesListener(this);
        Singleton.getInstance(getContext()).getLines(getContext());

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance(getContext()).addInvoiceAPI(createInvoice(), getContext());
                Singleton.getInstance(getContext()).getLines(getContext());
            }
        });

        return view;
    }

    private Invoice createInvoice() {
        float total = 0;

        for(int i=0; i<binding.listShop.getAdapter().getCount(); i++) {
            Invoice_line auxLine = (Invoice_line) binding.listShop.getAdapter().getItem(i);
            if (auxLine.isSelected()) {
                total+=auxLine.getTotal();
                Singleton.getInstance(getContext()).removeLineAPI(auxLine, getContext());
            }
        }

        return
                new Invoice(
                        0,
                        geral_.getFromDate(new Date()),
                        total,
                        userPreferences.getInt(LoginActivity.RESERVATION_ID, 0),
                        1
                );
    }

    @Override
    public void onRefreshLinesList(ArrayList<Invoice_line> lines) {
        if (lines != null) {
            binding.listShop.setAdapter(new ListLineAdapter(getContext(), lines));
        }
    }
}