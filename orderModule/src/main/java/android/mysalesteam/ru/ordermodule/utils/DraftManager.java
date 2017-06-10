package android.mysalesteam.ru.ordermodule.utils;

import android.mysalesteam.ru.ordermodule.models.Draft;
import android.mysalesteam.ru.ordermodule.models.Sku;

import java.util.List;

/**
 * Created by Alexey Nekrasov on 07.12.16.
 */

public class DraftManager {

    static final String ACTION_SKU_LIST_CHANGED = "ru.mysalesteam.android.myorder.draft_manager.sku_list_changed";

    private static DraftManager instance;
    private Draft draft;

    public static DraftManager getInstance() {
        if (instance == null) {
            instance = new DraftManager();
        }

        return instance;
    }

    private DraftManager() {
        getFromCache();
    }

    public void onSkuQuantityChanged(long companyId, Sku sku) {

        if (companyId == draft.getCompanyId()) {
            Sku draftSku = draft.getSkuById(sku.getExternalId());
            if (draftSku == null) {
                draft.addSku(sku);
            } else {
                draftSku.setQuantity(sku.getQuantity());
            }
        } else {
            resetDraft();
            draft.setCompanyId(companyId);
            draft.addSku(sku);
        }
    }

    public void bindWithDraft(long companyId, List<Sku> skuList) {
        if (companyId == draft.getCompanyId()) {
            for (Sku sku : skuList) {
                Sku skuDraft = draft.getSkuById(sku.getExternalId());
                if (skuDraft != null) {
                    sku.setQuantity(skuDraft.getQuantity());
                }
            }
        }
    }

    public List<Sku> getSkus() {
        return draft.getSkus();
    }

    public Draft getDraft() {
        return draft;
    }

    public float getValue() {
        List<Sku> skus = draft.getSkus();
        float value = 0;

        for (Sku sku : skus) {
            value += sku.getValue();
        }

        return value;
    }



    void setStoreId(String storeId) {
        draft.setStoreId(storeId);
    }

    String getStoreId() {
        return draft.getStoreId();
    }

    long getCompanyId() {
        return draft.getCompanyId();
    }

    int getSkuCount() {
        return draft.getSkus().size();
    }

    public boolean isEmpty() {
        return draft.getSkus().isEmpty();
    }

    private void clearEmptySkus() {
        List<Sku> skus = draft.getSkus();
        for (int i = 0; i < skus.size(); i++) {
            if (skus.get(i).getQuantity() == 0) {
                skus.remove(i);
            }
        }
    }

    public void saveToCache() {
      /*  clearEmptySkus();
        CacheDataBase.saveCached(CacheDataBase.CACHE_DRAFT, draft.toJsonString());
        LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(new Intent(ACTION_SKU_LIST_CHANGED));*/
    }

    private void getFromCache() {
        /*String cachedDraft = CacheDataBase.getCached(CacheDataBase.CACHE_DRAFT);

        if (cachedDraft != null) {
            draft = App.getGson().fromJson(cachedDraft, Draft.class);
        } else {
            draft = new Draft();
        }*/
    }

    public void resetDraft() {
        draft.setCompanyId(-1);
        draft.getSkus().clear();
        saveToCache();
    }
}