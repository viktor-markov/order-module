package android.mysalesteam.ru.ordermodule.ui;

import android.mysalesteam.ru.ordermodule.models.SkuCategory;

import java.util.List;


/**
 * Created by II on 22.01.2017.
 */

public interface SkusView {
    void showProgress();
    void hideProgress();
    void showError();
    void showEmpty();
    void setCategoryData(List<SkuCategory> data);
    void setSkusData(SkuCategory data);
}
