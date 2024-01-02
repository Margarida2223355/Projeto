package com.example.pousadas.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.pousadas.activities.ReservationDetailActivity;
import com.example.pousadas.adapters.ListReservationAdapter;
import com.example.pousadas.databinding.FragmentReservationClientBinding;
import com.example.pousadas.listeners.ReservationsListener;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Reservation;
import com.example.pousadas.models.Singleton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class ReservationClientFragment extends Fragment implements ReservationsListener {

    private FragmentReservationClientBinding binding;
    private final Geral geral_ = new Geral();
    private MaterialAlertDialogBuilder alert;

    public ReservationClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReservationClientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        /* *************************************** */
        binding.txtInitDate.setText("2023-01-01");
        binding.txtEndDate.setText("2023-12-31");
        /* *************************************** */

        /* Criar a mensagem de alert */
        alert = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Erro")
                .setPositiveButton("OK", null);

        Singleton.getInstance(getContext()).setReservationsListener(this);

        /* Ao clicar no text field, abre o date picker */
        binding.calendarInits.setEndIconOnClickListener(new View.OnClickListener() {
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
                        binding.txtInitDate.setText(geral_.convertFromDateTxt(picker.getSelection()));
                    }
                });
            }
        });

        /* Ao clicar no text field, abre o date picker */
        binding.calendarEnd.setEndIconOnClickListener(new View.OnClickListener() {
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
                        binding.txtEndDate.setText(geral_.convertFromDateTxt(picker.getSelection()));
                    }
                });
            }
        });

        /* Listener para quando se clica na lupa para pesquisar as reservas */
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Verificar se as data estão preenchidas */
                if (TextUtils.isEmpty(binding.txtInitDate.getText())
                        ||
                        TextUtils.isEmpty(binding.txtEndDate.getText())) {

                    alert.setMessage("Falta inserir data!")
                            .create()
                            .show();

                }

                else {
                    Singleton.getInstance(getContext()).getReservationsByDates(geral_.convertToDate(binding.txtInitDate.getText().toString()), geral_.convertToDate(binding.txtEndDate.getText().toString()),getContext());
                }
            }
        });

        return view;
    }

    @Override
    public void onRefreshReservationsList(ArrayList<Reservation> reservations) {
        if (reservations != null) {
            binding.listRoom.setAdapter((new ListReservationAdapter(getContext(), reservations)));
        }

    }
}