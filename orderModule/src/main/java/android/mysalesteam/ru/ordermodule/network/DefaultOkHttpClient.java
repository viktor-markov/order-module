package android.mysalesteam.ru.ordermodule.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Alexey Nekrasov on 09.11.16.
 */

public class DefaultOkHttpClient {

    private static final long TIMEOUT = 10;

    private static Object lock = new Object();
    private static OkHttpClient okClient;

    public static OkHttpClient getOkClient() {
        synchronized (lock) {
            if (okClient == null) {
                okClient = new OkHttpClient.Builder()
                        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .build();
            }
            return okClient;
        }
    }
}
