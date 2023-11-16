package com.example.pousadas.enums;

public enum Role {
    ADMIN("Administrador"),
    FUNC("Funcionario"),
    CLIENT("Cliente");

    private String nome;

    Role(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
