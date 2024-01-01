package com.example.pousadas.enums;

public enum Status_Res {
    CARRINHO("Inativa"),
    PAGO("Ativa");

    private String status_res;

    Status_Res(String status_res) {
        this.status_res = status_res;
    }

    public String getStatus_res() {
        return status_res;
    }

    public static Status_Res getFromString(String text) {
        for (Status_Res st : Status_Res.values()) {
            if (st.status_res.equals(text)) {
                return st;
            }
        }

        throw new IllegalArgumentException("Não existe");
    }

}
