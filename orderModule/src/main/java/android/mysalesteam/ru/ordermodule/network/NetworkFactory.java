package android.mysalesteam.ru.ordermodule.network;

/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public class NetworkFactory {

    public static INetworkProvider getNetworkProvider() {
        return new NetworkProvider();
    }
}
