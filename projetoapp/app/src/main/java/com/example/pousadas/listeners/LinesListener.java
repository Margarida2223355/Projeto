package com.example.pousadas.listeners;

import com.example.pousadas.models.Food;
import com.example.pousadas.models.Invoice_line;

import java.util.ArrayList;

public interface LinesListener {
    void onRefreshLinesList(ArrayList<Invoice_line> lines);
}
