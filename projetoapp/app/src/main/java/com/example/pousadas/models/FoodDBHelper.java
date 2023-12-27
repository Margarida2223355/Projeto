package com.example.pousadas.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pousadas.enums.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "projeto";
    private static final String DB_TABLE = "refeicao";
    private static final  int DB_VERSION = 1;
    private final SQLiteDatabase db;
    private static final String ID = "id";
    private static final String NAME = "nome";
    private static final String PRICE = "preco";
    private static final String DATE = "data";
    private static final String SCHEDULE = "horario";
    private Geral geral_ = new Geral();

    public FoodDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFoodsTable =
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
                + SCHEDULE
                + " TEXT CHECK("
                + SCHEDULE
                + " IN ('Jantar', 'Almoço')) NOT NULL);";

        db.execSQL(createFoodsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        this.onCreate(db);
    }

    /* Método para guardar todas as refeições na BD local */
    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foods = new ArrayList<>();
        Cursor cursor = this.db.query(DB_TABLE, new String[] {ID, NAME, PRICE, DATE, SCHEDULE}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Food auxFood = new Food(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        geral_.convertToDate(cursor.getString(3)),
                        Schedule.getFromString(cursor.getString(4))
                );

                foods.add(auxFood);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return foods;
    }

    public void removeAllFoodsDB() {
        this.db.delete(DB_TABLE, null, null);
    }

    public void addFoodDB(Food food) {
        ContentValues values = new ContentValues();

        values.put(NAME, food.getName());
        values.put(PRICE, food.getPrice());
        values.put(DATE, convertFromDate(food.getDate()));
        values.put(SCHEDULE, food.getSchedule().toString());

        this.db.insert(DB_TABLE, null, values);
    }

    public String convertFromDate(Object date) {
        /* Definir formato da data*/
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        /* Devolve data em String com o formato definido */
        return format.format(geral_.getFromDate((Date) date));
    }

    public Date convertToDate(String date) {
        /* Definir formato da data*/
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        /* Devolve data em String com o formato definido */
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
