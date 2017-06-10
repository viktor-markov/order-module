package android.mysalesteam.ru.ordermodule.ui;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by viktormarkov on 09.01.17.
 */

public abstract class BasePresenter<V> {

    private WeakReference<V> mView;

    protected void resetState() {
    }

    public void bindView(@NonNull V view) {
        mView = new WeakReference<>(view);
        if (setupDone()) {
            updateView();
        } else {
            loadData();
        }
    }

    public void unbindView() {
        mView = null;
    }

    protected V getView() {
        if (mView == null) {
            return null;
        } else {
            return mView.get();
        }
    }

    protected boolean viewIsNotNull() {
        return mView != null;
    }

    protected abstract void loadData();

    protected abstract void updateView();

    protected abstract boolean setupDone();
}
