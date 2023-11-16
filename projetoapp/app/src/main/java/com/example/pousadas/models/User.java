package com.example.pousadas.models;

import com.example.pousadas.enums.Role;

public class User {
    private String nome, email, username, password;
    private int idade;
    private Role role;

    public User() {
    }

    public User(String nome, String email, String username, String password, int idade, Role role) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.idade = idade;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
