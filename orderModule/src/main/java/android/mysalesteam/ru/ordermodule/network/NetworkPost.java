package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.KitSettings;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public abstract class NetworkPost extends DefaultNetworkFetcher {
    protected abstract RequestBody getEntity() throws IOException;

    @Override
    public Request prepare(String sessionId) throws IOException {
        final Request.Builder requestBuilder = new Request.Builder().url(KitSettings.getApiUrl() + getUriSuffix());
        setHeaders(requestBuilder, sessionId);
        requestBuilder.post(getEntity());
        return requestBuilder.build();
    }
}
