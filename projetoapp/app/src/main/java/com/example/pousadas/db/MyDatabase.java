package com.example.pousadas.db;

import android.provider.BaseColumns;

public class MyDatabase {
    public MyDatabase() {
    }

    /* Tabela Refeições */
    public static class FoodTable implements BaseColumns {
        public static final String DB_TABLE = "refeicao";
        public static final String ID = "id";
        public static final String NAME = "nome";
        public static final String PRICE = "preco";
        public static final String DATE = "data";
        public static final String CATEGORY  = "categoria";

        public String createFoodTable() {
            return
                    "CREATE TABLE "
                            + DB_TABLE
                            + "("
                            + ID
                            + " INTEGER PRIMARY KEY, "
                            + NAME
                            + " TEXT NOT NULL, "
                            + PRICE
                            + " DOUBLE NOT NULL, "
                            + DATE
                            + " DATE NOT NULL, "
                            + CATEGORY
                            + " TEXT CHECK("
                            + CATEGORY
                            + " IN ('Jantar', 'Almoco')) NOT NULL);";
        }
    }

    /* Tabela Serviços */
    public static class ServiceTable implements BaseColumns {
        public static final String DB_TABLE = "servico";
        public static final String ID = "id";
        public static final String NAME = "nome";
        public static final String DESCRIPTION = "descricao";
        public static final String PRICE = "preco";

        public String createServiceTable() {
            return
                    "CREATE TABLE "
                            + DB_TABLE
                            + "("
                            + ID
                            + " INTEGER PRIMARY KEY, "
                            + NAME
                            + " TEXT NOT NULL, "
                            + DESCRIPTION
                            + " TEXT NOT NULL, "
                            + PRICE
                            + " DOUBLE NOT NULL);";
        }
    }

    /* Tabela Reservas */
    public static class ReservationTable implements BaseColumns {
        public static final String DB_TABLE = "reserva";
        public static final String ID = "id";
        public static final String INIT_DATE = "data_inicial";
        public static final String END_DATE = "data_final";
        public static final String PRICE = "preco_total";
        public static final String STATUS = "status";
        public static final String CLIENT_ID = "cliente_id";
        public static final String ROOM_ID = "quarto_id";

        public String createReservationTable() {
            return
                    "CREATE TABLE "
                            + DB_TABLE
                            + "("
                            + ID
                            + " INTEGER PRIMARY KEY, "
                            + INIT_DATE
                            + " DATE NOT NULL, "
                            + END_DATE
                            + " DATE NOT NULL, "
                            + PRICE
                            + " DOUBLE NOT NULL, "
                            + STATUS
                            + " TEXT CHECK("
                            + STATUS
                            + " IN ('Carrinho', 'Pago', 'Cancelado')) NOT NULL, "
                            + CLIENT_ID
                            + " INTEGER NOT NULL, "
                            + ROOM_ID
                            + " INTEGER NOT NULL, "
                            + " FOREIGN KEY(" + CLIENT_ID + ") REFERENCES Cliente(id), "
                            + " FOREIGN KEY(" + ROOM_ID + ") REFERENCES Quarto(id));";
        }
    }

    /* Tabela Users */
    public static class UserTable implements BaseColumns {
        public static final String DB_TABLE = "inf_user";
        public static final String ID = "id";
        public static final String NOME_COMPLETO = "nome_completo";
        public static final String MORADA = "morada";
        public static final String PAIS = "pais";
        public static final String TELEFONE = "telefone";
        public static final String SALARIO = "salario";
        public static final String NIF = "nif";

        public String createReservationTable() {
            return
                    "CREATE TABLE "
                            + DB_TABLE
                            + "("
                            + ID
                            + " INTEGER PRIMARY KEY, "
                            + NOME_COMPLETO
                            + " TEXT NOT NULL, "
                            + MORADA
                            + " TEXT NOT NULL, "
                            + PAIS
                            + " TEXT NOT NULL, "
                            + TELEFONE
                            + " TEXT NOT NULL, "
                            + SALARIO
                            + " DOUBLE NOT NULL, "
                            + NIF
                            + " TEXT NOT NULL);";

        }
    }
}
