package com.example.pousadas.models;

public class InvoiceLine_Has_Invoice {
    private Invoice invoice;
    private Invoice_line invoiceLine;

    public InvoiceLine_Has_Invoice(Invoice invoice, Invoice_line invoiceLine) {
        this.invoice = invoice;
        this.invoiceLine = invoiceLine;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Invoice_line getInvoiceLine() {
        return invoiceLine;
    }
}
