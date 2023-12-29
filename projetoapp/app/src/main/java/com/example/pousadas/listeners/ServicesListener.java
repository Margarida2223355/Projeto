package com.example.pousadas.listeners;

import com.example.pousadas.models.Service;

import java.util.ArrayList;

public interface ServicesListener {
    void onRefreshServicesList(ArrayList<Service> services);
}
