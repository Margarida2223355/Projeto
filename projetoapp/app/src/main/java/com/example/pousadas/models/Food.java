package com.example.pousadas.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.pousadas.enums.Category;

import java.io.Serializable;
import java.util.Date;


public class Food implements Parcelable {

    /* Propriedades da classe Food:
     *
     * Preço - float
     * Nome - String
     * id - Int
     * Data - Date
     * Horário - Category
     */
    private float price;
    private String name;
    private int id, qty;
    private Date date;
    private Category category;

    /* Construtor desta classe - é necessário ter todas as propriedades */
    public Food(int id, String name, Float price, Date date, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
        qty = 0;
    }

    /* Get e Post
     *
     * Price - get e post
     * Name - get e post
     * Id - get
     * Qty - get
     * Date - get e post
     * Category - get e post
     */

    protected Food(Parcel in) {
        price = in.readFloat();
        name = in.readString();
        id = in.readInt();
        qty = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(qty);
        dest.writeLong(date.getTime());
    }
}
