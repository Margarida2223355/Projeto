package com.example.pousadas.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.pousadas.enums.Status;

public class Invoice_line implements Parcelable {
    private int id, qty;
    private float total, unit_price;
    private Status status;
    private Food food;
    private int reservation_id;
    private Service service;
    private boolean isSelected;

    public Invoice_line(int id, int qty, Service service, Food food, float total, float unit_price, int reservation_id, Status status) {
        this.id = id;
        this.qty = qty;
        this.total = total;
        this.unit_price = unit_price;
        this.status = status;
        this.food = food;
        this.reservation_id = reservation_id;
        this.service = service;
    }

    protected Invoice_line(Parcel in) {
        id = in.readInt();
        qty = in.readInt();
        total = in.readFloat();
        unit_price = in.readFloat();
        reservation_id = in.readInt();
        isSelected = in.readByte() != 0;
        status = in.readParcelable(Status.class.getClassLoader());
        food = in.readParcelable(Food.class.getClassLoader());
        service = in.readParcelable(Service.class.getClassLoader());
    }

    public static final Creator<Invoice_line> CREATOR = new Creator<Invoice_line>() {
        @Override
        public Invoice_line createFromParcel(Parcel in) {
            return new Invoice_line(in);
        }

        @Override
        public Invoice_line[] newArray(int size) {
            return new Invoice_line[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getReservation() {
        return reservation_id;
    }

    public void setReservation(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(qty);
        dest.writeFloat(total);
        dest.writeFloat(unit_price);
        dest.writeInt(reservation_id);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeParcelable(status, flags);
        dest.writeParcelable(food, flags);
        dest.writeParcelable(service, flags);
    }
}
