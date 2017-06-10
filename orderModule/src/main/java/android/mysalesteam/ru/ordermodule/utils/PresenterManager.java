package android.mysalesteam.ru.ordermodule.utils;

import android.mysalesteam.ru.ordermodule.ui.BasePresenter;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by viktormarkov on 09.01.17.
 */

public class PresenterManager {
    private static final String KEY_PRESENTER_ID = "presenter_id";
    private static PresenterManager instance;

    private final AtomicLong currentId;

    private final Map<Long, BasePresenter<?>> presenters;

    private PresenterManager() {
        currentId = new AtomicLong();
        presenters = new HashMap<>();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager();
        }
        return instance;
    }

    /**
     * Восстанавливает экземпляр Presenter после смены конфигурации устройства.
     *
     * Принимает Bundle из метода onCreate и извлекает из него уникальный ключ, сгенерированный
     * во время сохранения в методе {@link #savePresenter(BasePresenter, Bundle)}.
     * Далее по этому ключу вынимает Presenter из HashMap и возвращает в вызывающий код.
     */
    public <P extends BasePresenter<?>> P restorePresenter(Bundle savedInstanceState) {
        P presenter = null;

        if (savedInstanceState != null) {
            Long presenterId = savedInstanceState.getLong(KEY_PRESENTER_ID);
            BasePresenter<?> basePresenter = presenters.get(presenterId);

            if (basePresenter != null) {
                presenter = (P) basePresenter;
                presenters.remove(presenterId);
            }
        }
        return presenter;
    }

    /**
     * Сохраняет экземпляр Presenter во время смены конфигурации устройства.
     *
     * Принимает экземпляр Presenter и Bundle из onSaveInstanceState.
     * Далее создает уникальный ключ.
     * Под этим ключом Presenter помещается в HashMap, также ключ сохраняется в Bundle.
     */
    public void savePresenter(BasePresenter<?> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(KEY_PRESENTER_ID, presenterId);
    }
}
