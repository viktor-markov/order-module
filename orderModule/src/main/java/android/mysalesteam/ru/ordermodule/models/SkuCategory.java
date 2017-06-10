package android.mysalesteam.ru.ordermodule.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexn on 01.12.2016.
 */

public class SkuCategory implements Parcelable {

    private String name;
    private List<Sku> skus = new ArrayList<>();

    public SkuCategory(String name, List<Sku> skus) {
        this.name = name;
        this.skus = skus;
    }

    public SkuCategory(String name) {
        this.name = name;
    }

    private SkuCategory(Parcel in) {
        name = in.readString();
        skus = in.createTypedArrayList(Sku.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(skus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SkuCategory> CREATOR = new Creator<SkuCategory>() {
        @Override
        public SkuCategory createFromParcel(Parcel in) {
            return new SkuCategory(in);
        }

        @Override
        public SkuCategory[] newArray(int size) {
            return new SkuCategory[size];
        }
    };

    public String getName() {
        return name;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void addSku(Sku sku) {
        skus.add(sku);
    }
}
