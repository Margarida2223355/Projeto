package com.example.pousadas.listeners;

import com.example.pousadas.models.Food;
import com.example.pousadas.models.Invoice;
import com.example.pousadas.models.Invoice_line;

import java.util.ArrayList;
import java.util.List;

public interface InvoiceListener {
    void onInvoice(Invoice invoice, List<Invoice_line> lines);
}
