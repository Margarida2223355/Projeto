package com.example.pousadas.enums;

public enum Status {
    CARRINHO("Carrinho"),
    PAGO("Pago"),
    CANCELADO("Cancelado");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status getFromString(String text) {
        for (Status st : Status.values()) {
            if (st.status.equals(text)) {
                return st;
            }
        }

        throw new IllegalArgumentException("Não existe");
    }

}
