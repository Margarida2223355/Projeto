package com.example.pousadas.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Service implements Parcelable {

    /* Propriedades da classe Service:
     *
     * Preço - float
     * Descrição - String
     * Nome - String
     * id - Int
     * qty - int
     */
    private float price;
    private String description, name;
    private int id, qty;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Service(int id, String name, String description, float price) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.id = id;
        this.qty = 0;
    }

    protected Service(Parcel in) {
        price = in.readFloat();
        description = in.readString();
        name = in.readString();
        id = in.readInt();
        qty = in.readInt();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    /* Get e Post
     *
     * Price - get e post
     * Description - get e post
     * Name - get e post
     * Id - get
     * Qty - get
     */
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    /* Métodos para Incrementar ou Decrementar quantidades */
    public void addQty() {
        qty++;
    }

    public void remQty() {
        if (qty > 0) qty--;
    }

    /* Método para fazer reset à quantidade
     *
     * Utilizado quando vamos adicionar ao carrinho.
     *
     */
    public void resetQty() {
        qty = 0;
    }

    public float getTotal() {
        return
                qty * price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeFloat(price);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(qty);
    }
}
