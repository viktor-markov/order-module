package android.mysalesteam.ru.ordermodule.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexn on 01.12.2016.
 */

public class Sku implements Parcelable {
    private String externalId;
    private String name;
    private String category;
    private float price;
    private int quantity = 0;
    private String face;

    public Sku(String externalId, String name, String category, float price, int quantity, String face) {
        this.externalId = externalId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.face = face;
    }

    private Sku(Parcel in) {
        externalId = in.readString();
        name = in.readString();
        category = in.readString();
        price = in.readFloat();
        quantity = in.readInt();
        face = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(externalId);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeFloat(price);
        dest.writeInt(quantity);
        dest.writeString(face);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sku> CREATOR = new Creator<Sku>() {
        @Override
        public Sku createFromParcel(Parcel in) {
            return new Sku(in);
        }

        @Override
        public Sku[] newArray(int size) {
            return new Sku[size];
        }
    };

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getValue() {
        return price * quantity;
    }

    public String getFace() {
        return face;
    }
}
