package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.KitSettings;

import okhttp3.Request;

/**
 * Basic class to process GET requests
 */

public abstract class NetworkGet extends DefaultNetworkFetcher {
    @Override
    public Request prepare(String sessionId) {
        final Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(KitSettings.getApiUrl() + getUriSuffix());
        setHeaders(requestBuilder, sessionId);
        return requestBuilder.build();
    }
}
