package android.mysalesteam.ru.ordermodule.models;

import android.mysalesteam.ru.ordermodule.KitSettings;
import android.mysalesteam.ru.ordermodule.models.Sku;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * Created by Alexey Nekrasov on 08.12.16.
 */

public class Draft {

    private long companyId = -1;
    private String storeId = "";
    private List<Sku> skus = new ArrayList<>();

    public Draft() {

    }

    public Sku getSkuById(String externalId) {
        for (Sku sku : skus) {
            if (sku.getExternalId().equals(externalId)) {
                return sku;
            }
        }

        return null;
    }

    public void addSku(Sku sku) {
        skus.add(sku);

        final Collator collator = Collator.getInstance(new Locale("ru", "RU"));
        Collections.sort(skus, new Comparator<Sku>() {
            @Override
            public int compare(Sku o1, Sku o2) {
                return collator.compare(o1.getName(), o2.getName());
            }
        });
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public long getCompanyId() {
        return companyId;
    }



    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String toJsonString() {
        return KitSettings.getGson().toJson(this);
    }
}
