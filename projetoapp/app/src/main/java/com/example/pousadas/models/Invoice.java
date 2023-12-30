package com.example.pousadas.models;

import java.util.Date;

public class Invoice {
    private int id;
    private Date payment_date = null;
    private float total_price = .0F, total_w_out_tax= .0F;
    private Reservation reservation;
    private Lodge lodge;
    private InvoiceLine_Has_Invoice invoiceLineHasInvoice;
    private Invoice_line invoiceLine;
}
