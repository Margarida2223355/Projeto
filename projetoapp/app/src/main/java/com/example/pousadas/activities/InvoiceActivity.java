package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListInvoiceAdapter;
import com.example.pousadas.databinding.ActivityInvoiceBinding;
import com.example.pousadas.db.DBHelper;
import com.example.pousadas.fragments.ShopClientFragment;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Invoice;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Singleton;
import com.example.pousadas.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {
    private ActivityInvoiceBinding binding;
    private User user;
    private Geral geral_ = new Geral();
    private Invoice invoice;
    private ArrayList<Invoice_line> lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        Intent intent = getIntent();
        lines = (ArrayList<Invoice_line>) intent.getSerializableExtra(ShopClientFragment.LINES);
    }

}