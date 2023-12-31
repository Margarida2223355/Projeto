package com.example.pousadas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pousadas.enums.Category;
import com.example.pousadas.enums.Status;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Geral;
import com.example.pousadas.models.Reservation;
import com.example.pousadas.models.Room;
import com.example.pousadas.models.Service;
import com.example.pousadas.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "projeto";
    private final SQLiteDatabase db;
    private static final int DB_VERSION = 1;
    private Geral geral_ = new Geral();

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(new MyDatabase.FoodTable().createFoodTable());
        db.execSQL(new MyDatabase.ServiceTable().createServiceTable());
        db.execSQL(new MyDatabase.ReservationTable().createReservationTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyDatabase.FoodTable.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MyDatabase.ServiceTable.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MyDatabase.ReservationTable.DB_TABLE);
        this.onCreate(db);
    }

    public class FoodsTable {
        /* Método para guardar todas as refeições na BD local */
        public ArrayList<Food> getAllFoods() {
            ArrayList<Food> foods = new ArrayList<>();
            Cursor cursor = db.query(MyDatabase.FoodTable.DB_TABLE,
                    new String[] {MyDatabase.FoodTable.ID, MyDatabase.FoodTable.NAME, MyDatabase.FoodTable.PRICE, MyDatabase.FoodTable.DATE, MyDatabase.FoodTable.CATEGORY},
                    null,
                    null,
                    null,
                    null,
                    null);

            if (cursor.moveToFirst()) {
                do {
                    Food auxFood = new Food(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getFloat(2),
                            geral_.convertToDateDB(cursor.getString(3)),
                            Category.getFromString(cursor.getString(4))
                    );

                    foods.add(auxFood);

                } while (cursor.moveToNext());

                cursor.close();
            }

            return foods;
        }

        public void removeAllFoodsDB() {
            db.delete(MyDatabase.FoodTable.DB_TABLE, null, null);
        }

        public void addFoodDB(Food food) {
            ContentValues values = new ContentValues();

            values.put(MyDatabase.FoodTable.NAME, food.getName());
            values.put(MyDatabase.FoodTable.PRICE, food.getPrice());
            values.put(MyDatabase.FoodTable.DATE, convertFromDate(food.getDate()));
            values.put(MyDatabase.FoodTable.CATEGORY, food.getCategory().toString());

            db.insert(MyDatabase.FoodTable.DB_TABLE, null, values);
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
    public class ServicesTable {
        public ArrayList<Service> getAllServices() {
            ArrayList<Service> services = new ArrayList<>();
            Cursor cursor = db.query(MyDatabase.ServiceTable.DB_TABLE,
                    new String[] {MyDatabase.ServiceTable.ID, MyDatabase.ServiceTable.NAME, MyDatabase.ServiceTable.DESCRIPTION, MyDatabase.ServiceTable.PRICE},
                    null,
                    null,
                    null,
                    null,
                    null);

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
            db.delete(MyDatabase.ServiceTable.DB_TABLE, null, null);
        }

        public void addServiceDB(Service service) {
            ContentValues values = new ContentValues();

            values.put(MyDatabase.ServiceTable.NAME, service.getName());
            values.put(MyDatabase.ServiceTable.DESCRIPTION, service.getDescription());
            values.put(MyDatabase.ServiceTable.PRICE, service.getPrice());

            db.insert(MyDatabase.ServiceTable.DB_TABLE, null, values);
        }

    }
    public class ReservationsTable {
        public ArrayList<Reservation> getAllReservations() {
            ArrayList<Reservation> reservations = new ArrayList<>();
            Cursor cursor = db.query(
                    MyDatabase.ReservationTable.DB_TABLE,
                new String[] {MyDatabase.ReservationTable.ID, MyDatabase.ReservationTable.INIT_DATE, MyDatabase.ReservationTable.END_DATE, MyDatabase.ReservationTable.PRICE, MyDatabase.ReservationTable.STATUS, MyDatabase.ReservationTable.CLIENT_ID, MyDatabase.ReservationTable.ROOM_ID},
                    null,
                    null,
                    null,
                    null,
                    null);

            if (cursor.moveToFirst()) {
                do {
                    Reservation auxReservation = new Reservation(
                            cursor.getInt(0),
                            geral_.convertToDateDB(cursor.getString(1)),
                            geral_.convertToDateDB(cursor.getString(2)),
                            cursor.getFloat(3),
                            Status.getFromString(cursor.getString(4)),
                            getUserDetailsFromDatabase(cursor.getInt(5)),
                            getRoomDetailsFromDatabase(cursor.getInt(6))
                    );

                    reservations.add(auxReservation);

                } while (cursor.moveToNext());

                cursor.close();
            }

            return reservations;
        }

        private Room getRoomDetailsFromDatabase(int anInt) {
            Room auxRoom = null;

            Cursor cursor = db.rawQuery(
                    "SELECT * FROM quarto WHERE id = " + anInt,
                    null
            );

            if (cursor.moveToFirst()) {
                auxRoom = new Room(
                        anInt,
                        cursor.getString(1),
                        (float) cursor.getDouble(2)
                );
            }

            cursor.close();

            return auxRoom;
        }

        private User getUserDetailsFromDatabase(int anInt) {
            User auxUser = null;

            Cursor cursor = db.rawQuery(
                    "SELECT * FROM inf_user WHERE id = " + anInt,
                    null
            );

            if (cursor.moveToFirst()) {
                auxUser = new User(
                        anInt,
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        (float) cursor.getDouble(5),
                        cursor.getString(6)
                );
            }

            cursor.close();

            return auxUser;
        }

        public void removeAllReservationsDB() {
            db.delete(MyDatabase.ReservationTable.DB_TABLE, null, null);
        }

        public void addReservationDB(Reservation reservation) {
            ContentValues values = new ContentValues();

            values.put(MyDatabase.ReservationTable.INIT_DATE, convertFromDate(reservation.getInit_date()));
            values.put(MyDatabase.ReservationTable.END_DATE, convertFromDate(reservation.getEnd_date()));
            values.put(MyDatabase.ReservationTable.PRICE, reservation.getTotal_price());
            values.put(MyDatabase.ReservationTable.STATUS, reservation.getStatus().toString());
            values.put(MyDatabase.ReservationTable.CLIENT_ID, reservation.getClient().getId());
            values.put(MyDatabase.ReservationTable.ROOM_ID, reservation.getRoom().getId());

            db.insert(MyDatabase.ReservationTable.DB_TABLE, null, values);
        }

        public String convertFromDate(Object date) {
            /* Definir formato da data*/
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

            /* Devolve data em String com o formato definido */
            return format.format(geral_.getFromDate((Date) date));
        }

    }

}
