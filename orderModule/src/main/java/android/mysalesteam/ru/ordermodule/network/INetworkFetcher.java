package android.mysalesteam.ru.ordermodule.network;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alexey Nekrasov on 08.11.16.
 */

public interface INetworkFetcher {
    String getApiVersion();
    String getUriSuffix();
    Map<String, String> getHeaders(String sessionId);
    void checkStatusCode(Response response) throws IOException, SessionExpiredException;
    Request prepare(String sessionId) throws IOException;
    OkHttpClient getOkClient();
    String process(Response response) throws IOException;
}
