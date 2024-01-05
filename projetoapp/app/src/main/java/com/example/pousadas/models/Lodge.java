package com.example.pousadas.models;

import java.util.ArrayList;

public class Lodge {
    private int id;
    private String name, address, vat;
    private ArrayList<Invoice> invoices;

    public Lodge(int id, String name, String address, String vat, ArrayList<Invoice> invoices) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.vat = vat;
        this.invoices = invoices;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
}