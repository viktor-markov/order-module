package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.models.Sku;

import java.util.List;


/**
 * Created by Alexey Nekrasov on 10.11.16.
 */

public interface IRepository {

    List<Sku> getSkus(long companyId, String token);
    PostOrderWrapper postOrder(String token);

}
