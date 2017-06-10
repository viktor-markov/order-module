package android.mysalesteam.ru.ordermodule.ui;

import android.mysalesteam.ru.ordermodule.network.RepositoryFactory;
import android.mysalesteam.ru.ordermodule.models.Sku;
import android.mysalesteam.ru.ordermodule.models.SkuCategory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * Created by viktormarkov on 09.01.17.
 */

public class SkusPresenter extends BasePresenter<SkusView> {

    private String mToken;
    private String mStoreId;
    private long mBusinessId;
    private List<SkuCategory> mData;

    private ScreenMode mScreenMode;
    private SkuCategory mCurrentCategory;

    enum ScreenMode {
        MODE_CATEGORY,
        MODE_SKU;
    }

    SkusPresenter(String token, String storeId, long businessId) {
       mToken = token;
        mStoreId = storeId;
        mBusinessId = businessId;

        mScreenMode = ScreenMode.MODE_CATEGORY;
    }

    @Override
    public void bindView(@NonNull SkusView view) {
        super.bindView(view);
    }

    @Override
    protected void loadData() {
       new LoadSkusTask().execute();
    }

    @Override
    public void unbindView() {
        super.unbindView();
    }

    @Override
    protected void updateView() {
        if (viewIsNotNull()) {

            getView().hideProgress();

            if (mData != null) {
                switch (mScreenMode) {
                    case MODE_CATEGORY:
                        if (mData.size() > 0) {
                            getView().setCategoryData(mData);
                        } else {
                            getView().showEmpty();
                        }
                        break;
                    case MODE_SKU:
                        int pos = findCategory(mCurrentCategory, mData);
                        if (pos != -1) {
                            getView().setSkusData(mData.get(pos));
                        } else {
                            getView().showError();
                        }
                        break;
                }
            } else {
                getView().showError();
            }
        }
    }

    @Override
    protected boolean setupDone() {
        return getView() != null && mData != null;
    }

    void setCurrentCategory(SkuCategory currentCategory) {
        mCurrentCategory = currentCategory;
    }

    void setScreenMode(ScreenMode mode) {
        mScreenMode = mode;
    }

    ScreenMode getScreenMode() {
        return mScreenMode;
    }

    List<SkuCategory> getCategoryData() {
        return mData;
    }

    private int findCategory(SkuCategory category, List<SkuCategory> categoriesList) {
        for (int i = 0; i < categoriesList.size(); i++) {
            SkuCategory cat = categoriesList.get(i);
            if (cat.getName().equals(category.getName())) {
                return i;
            }
        }
        return -1;
    }

    private class LoadSkusTask extends AsyncTask<Void, Void, List<SkuCategory>> {

        @Override
        protected List<SkuCategory> doInBackground(Void... voids) {

            if (viewIsNotNull()) {
                getView().showProgress();
            }

            List<Sku> skus = RepositoryFactory.getRepository().getSkus(mBusinessId, mToken);
            return skus == null ? null : getSortedCategoriesList(mBusinessId, skus);
        }

        @Override
        protected void onPostExecute(List<SkuCategory> data) {
            super.onPostExecute(data);

            if (data != null) {
                mData = data;
            }

            updateView();
        }


    }

    private List<SkuCategory> getSortedCategoriesList(long companyId, List<Sku> skus) {

        List<SkuCategory> categories = new ArrayList<>();

        top: for (Sku sku : skus) {
            for (SkuCategory category : categories) {
                if (category.getName().equals(sku.getCategory())) {
                    category.addSku(sku);
                    continue top;
                }
            }
            SkuCategory skuCategory = new SkuCategory(sku.getCategory());
            skuCategory.addSku(sku);
            categories.add(skuCategory);
        }

        final Collator collator = Collator.getInstance(new Locale("ru", "RU"));

        Collections.sort(categories, new Comparator<SkuCategory>() {
            @Override
            public int compare(SkuCategory o1, SkuCategory o2) {
                return collator.compare(o1.getName(), o2.getName());
            }
        });

        for (SkuCategory category : categories) {
            Collections.sort(category.getSkus(), new Comparator<Sku>() {
                @Override
                public int compare(Sku o1, Sku o2) {
                    return collator.compare(o1.getName(), o2.getName());
                }
            });
        }

        //// TODO: 06.04.17 upgrade draft for mst
        /*DraftManager draftManager = DraftManager.getInstance();

        for (SkuCategory category : categories) {
            draftManager.bindWithDraft(companyId, category.getSkus());
        }*/

        return categories;
    }


}
