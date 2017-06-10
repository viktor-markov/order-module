package android.mysalesteam.ru.ordermodule.network;

import java.io.IOException;

/**
 * Created by Alexey Nekrasov on 09.11.16.
 */

public interface INetworkProvider {

    String fetch(INetworkFetcher fetcher, String token) throws IOException;

    <T> T fetch(INetworkFetcher fetcher, Class<T> clazz, String token) throws IOException;

    <T> T fetch(String result, Class<T> clazz);

    String fetchOrThrow(INetworkFetcher fetcher, String token) throws IOException, SessionExpiredException;
}
