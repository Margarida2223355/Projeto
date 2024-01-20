package com.example.pousadas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.adapters.ListInvoiceAdapter;
import com.example.pousadas.databinding.ActivityInvoiceBinding;
import com.example.pousadas.db.DBHelper;
import com.example.pousadas.fragments.ShopClientFragment;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Invoice;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.User;

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

        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = (new DBHelper(getApplicationContext())).new UserTable().getUser();
        invoice = getIntent().getParcelableExtra(ShopClientFragment.INVOICE);
        lines = getIntent().getParcelableArrayListExtra(ShopClientFragment.LINES);

        binding.listLines.setAdapter(new ListInvoiceAdapter(this, lines));

        binding.ClientName.setText(user.getNome());
        binding.InvoiceDate.setText(geral_.convertFromDate(invoice.getPayment_date()));
        binding.total.setText(invoice.getTotal_price() + "€");

        Toast.makeText(this, "Aqui", Toast.LENGTH_SHORT).show();
    }

}