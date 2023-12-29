package com.example.pousadas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pousadas.enums.Schedule;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.Geral;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServiceDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "projeto";
    private static final String DB_TABLE = "servico";
    //private static final int DB_VERSION = 1;
    private final SQLiteDatabase db;
    private static final String ID = "id";
    private static final String NAME = "nome";
    private static final String DESCRIPTION = "descricao";
    private static final String PRICE = "preco";
    private Geral geral_ = new Geral();

    public ServiceDBHelper(Context context, final int DB_VERSION) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createServicesTable =
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

        db.execSQL(createServicesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        this.onCreate(db);
    }

    /* Método para guardar todas as refeições na BD local */
    public ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<>();
        Cursor cursor = this.db.query(DB_TABLE, new String[] {ID, NAME, DESCRIPTION, PRICE}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Service auxService = new Service(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3)
                );

                services.add(auxService);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return services;
    }

    public void removeAllServicesDB() {
        this.db.delete(DB_TABLE, null, null);
    }

    public void addServiceDB(Service service) {
        ContentValues values = new ContentValues();

        values.put(NAME, service.getName());
        values.put(DESCRIPTION, service.getDescription());
        values.put(PRICE, service.getPrice());

        this.db.insert(DB_TABLE, null, values);
    }
}
