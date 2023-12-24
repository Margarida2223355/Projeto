package com.example.pousadas.enums;

public enum Role {
    ADMIN("Administrador"),
    FUNC("Funcionario"),
    CLIENT("Cliente");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
