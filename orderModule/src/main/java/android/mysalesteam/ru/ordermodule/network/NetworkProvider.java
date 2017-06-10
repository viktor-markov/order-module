package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.KitSettings;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public class NetworkProvider implements INetworkProvider {

    @Override
    public String fetch(INetworkFetcher fetcher, String token) throws IOException {

        try {
            return fetchOrThrow(fetcher, token);
        } catch (SessionExpiredException e) {
            throw new IOException("failed to reopen session for: " + fetcher.getClass().getSimpleName());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public <T> T fetch(INetworkFetcher fetcher, Class<T> clazz, String token) throws IOException {
        String result = fetch(fetcher, token);
        return fetch(result, clazz);
    }

    @Override
    public <T> T fetch(String result, Class<T> clazz) {
        return KitSettings.getGson().fromJson(result, clazz);
    }

    public String fetchOrThrow(INetworkFetcher fetcher, String token) throws IOException, SessionExpiredException {
        Request request = fetcher.prepare(token);
        Response response = null;
        try {
            response = fetcher.getOkClient().newCall(request).execute();
            fetcher.checkStatusCode(response);
            String result = fetcher.process(response);
            if (result != null && !result.isEmpty()) {
              //  throw new IOException("result is null or empty");
            }

            return result;
        } finally {
            // NOTE: fixed memory leak. We have to close body every time!!! See https://github.com/square/okhttp/issues/2311
            if (response != null) {
                response.body().close();
            }
        }
    }


}
