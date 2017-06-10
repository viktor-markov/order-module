package android.mysalesteam.ru.ordermodule.network;


/**
 * Created by Alexey Nekrasov on 14.11.16.
 */

public class RepositoryFactory {

    public static IRepository getRepository() {
        return new Repository();
    }
}
