package com.example.pousadas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListLineAdapter;
import com.example.pousadas.databinding.FragmentServicesClientBinding;
import com.example.pousadas.databinding.FragmentShopClientBinding;
import com.example.pousadas.listeners.LinesListener;
import com.example.pousadas.listeners.ServicesListener;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Singleton;

import java.util.ArrayList;

public class ShopClientFragment extends Fragment implements LinesListener {

    private FragmentShopClientBinding binding;

    public ShopClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShopClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Singleton.getInstance(getContext()).setLinesListener(this);
        Singleton.getInstance(getContext()).getLines(getContext());

        return view;
    }

    @Override
    public void onRefreshLinesList(ArrayList<Invoice_line> lines) {
        if (lines != null) {
            binding.listShop.setAdapter(new ListLineAdapter(getContext(), lines));
        }
    }
}