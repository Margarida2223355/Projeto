package com.example.pousadas.enums;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public enum Status implements Parcelable {
    CARRINHO("Carrinho"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado");

    private String status;

    Status(String status) {
        this.status = status;
    }

    Status(Parcel in) {
        status = in.readString();
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return Status.getFromString(in.readString());
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(status);
    }
}
