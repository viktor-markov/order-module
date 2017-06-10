package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.KitSettings;
import android.mysalesteam.ru.ordermodule.utils.GuavaSubstitute;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public abstract class DefaultNetworkFetcher implements INetworkFetcher {

    @Override
    public String getApiVersion() {
        return KitSettings.getApiVersion();
    }

    @Override
    public Map<String, String> getHeaders(String sessionId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token token=\"" + sessionId + "\"");
        return headers;
    }

    protected final void setHeaders(Request.Builder requestBuilder, String sessionId) {
        Map<String, String> headers = getHeaders(sessionId);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void checkStatusCode(Response response) throws IOException, SessionExpiredException {
        final int code = response.code();

        if (code < 200 || code >= 300) {
            String errorMsg = response.body().string();
            if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new SessionExpiredException();
            }

            String contentHeader = response.header("Content-Type");
            if (!contentHeader.isEmpty() && GuavaSubstitute.nullToEmpty(contentHeader).contains("application/json")) {
                // NOTE [2015-12-11 Kolpakov A.] : Со слов Егора и Жени это означает, что ответ обработан сервером и
                // поле ошибка должно быть непустым. Однако, гарантировать данное утверждения я не могу
                // поэтому через builder с внутренней проверкой
                throw MyOrderServerException.build(errorMsg, response.code(), response.message());
            } else {
                throw MyOrderServerException.build(null, response.code(), response.message());
            }
        }
    }

    @Override
    public OkHttpClient getOkClient() {
        return DefaultOkHttpClient.getOkClient();
    }

    @Override
    public String process(Response response) throws IOException {
       // String s = response.body().string();
        return response.body().string();
    }
}
