package com.example.pousadas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;

import java.util.ArrayList;
import java.util.Date;


public class FoodDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "projeto";
    private static final String DB_TABLE = "refeicao";
    private static final int DB_VERSION = 1;
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String PRECO = "preco";
    private static final String DATA = "data";
    private static final String HORARIO = "horario";
    private Geral geral__;

    private static final String CREATE_REFEICAO_TABLE =
            "CREATE TABLE "
                    + DB_TABLE
                    + "("
                    + ID + " INTEGER PRIMARY KEY, "
                    + NOME + " TEXT NOT NULL, "
                    + PRECO + " FLOAT NOT NULL, "
                    + DATA + " DATE NOT NULL, "
                    + HORARIO + " TEXT NOT NULL"
                    + ");";

    private final SQLiteDatabase db;

    public enum Horario {
        ALMOCO, JANTAR
    }

    public FoodDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_REFEICAO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        this.onCreate(db);
    }

    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foods = new ArrayList<>();
        Cursor cursor = this.db.query(DB_TABLE, new String[]{ID, NOME, PRECO, DATA, HORARIO}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        geral__.getDateFromString(cursor.getString(3)),
                        cursor.getString(4));

                foods.add(food);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return foods;
    }
}
