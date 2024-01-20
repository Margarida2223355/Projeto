package com.example.pousadas.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Invoice implements Parcelable {
    private int id;
    private Date payment_date;
    private float total_price;
    private int reservation_id;
    private int lodge_id;

    public Invoice(int id, Date payment_date, float total_price, int reservation_id, int lodge_id) {
        this.id = id;
        this.payment_date = payment_date;
        this.total_price = total_price;
        this.reservation_id = reservation_id;
        this.lodge_id = lodge_id;
    }

    protected Invoice(Parcel in) {
        id = in.readInt();
        payment_date = new Date(in.readLong());
        total_price = in.readFloat();
        reservation_id = in.readInt();
        lodge_id = in.readInt();
    }

    public static final Creator<Invoice> CREATOR = new Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel in) {
            return new Invoice(in);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };

    public int getId() {
        return id;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getReservation() {
        return reservation_id;
    }

    public void setReservation(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getLodge() {
        return lodge_id;
    }

    public void setLodge(int lodge_id) {
        this.lodge_id = lodge_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(payment_date.getTime());
        dest.writeFloat(total_price);
        dest.writeInt(reservation_id);
        dest.writeInt(lodge_id);
    }
}
