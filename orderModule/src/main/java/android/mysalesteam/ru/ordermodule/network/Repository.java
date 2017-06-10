package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.utils.DraftManager;
import android.mysalesteam.ru.ordermodule.models.Sku;
import android.mysalesteam.ru.ordermodule.models.SkusAnswer;

import java.io.IOException;
import java.util.List;


/**
 * Created by Alexey Nekrasov on 10.11.16.
 */
public class Repository implements IRepository {

    @Override
    public List<Sku> getSkus(long companyId, String token) {
        try {
            return NetworkFactory.getNetworkProvider().fetch(new SkusFetcher(companyId), SkusAnswer.class, token).skus;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public PostOrderWrapper postOrder(String token) {
        PostOrderWrapper wrapper = new PostOrderWrapper();
        try {
            DraftManager draftManager = DraftManager.getInstance();
            NetworkFactory.getNetworkProvider().fetch(new OrderPostFetcher(draftManager.getDraft()), token);
            draftManager.resetDraft();
            wrapper.setSuccess(true);
            return wrapper;
        } catch (IOException e) {
            wrapper.setSuccess(false);
            wrapper.setErrorCode(PostOrderWrapper.SOME_ERROR);
            return wrapper;
        }
    }
}
