package com.example.pousadas.models;

import android.database.Cursor;

import com.example.pousadas.enums.Role;

public class User {
    private int id;
    private String nome, morada, pais, telefone, nif;
    private float salario;
    private Role role;

    public User() {}

    public User(int id, String nome, String morada, String pais, String telefone, float salario, String nif) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.pais = pais;
        this.telefone = telefone;
        this.salario = salario;
        this.nif = nif;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
