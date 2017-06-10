package android.mysalesteam.ru.ordermodule;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by viktormarkov on 06.04.17.
 */

public class KitSettings {
    private static final int KIT_VERSION = 1;

    /*Набор возможных хостов*/
    private static final String MY_ORDER = "http://myorder.mstdev.ru";
    private static final String VADIM_LOCAL = "http://192.168.44.32:3000";

    /*Api version*/
    private static final String API_VERSION = "1";

    private static Gson sGson;

    public static int getKitVersion() {
        return KIT_VERSION;
    }

    public static synchronized String getApiVersion() {
        return API_VERSION;
    }

    public static synchronized String getApiUrl() {
        return MY_ORDER;
    }

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
        }

        return sGson;
    }
}
