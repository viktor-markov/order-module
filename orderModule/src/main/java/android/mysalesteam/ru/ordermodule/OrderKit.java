package android.mysalesteam.ru.ordermodule;

import android.content.Context;
import android.content.Intent;
import android.mysalesteam.ru.ordermodule.models.Sku;
import android.mysalesteam.ru.ordermodule.network.RepositoryFactory;
import android.mysalesteam.ru.ordermodule.ui.SkusActivity;
import android.mysalesteam.ru.ordermodule.utils.GuavaSubstitute;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by viktormarkov on 05.04.17.
 *
 * Main class to interact with OrderModule
 */

public class OrderKit {

    private String mToken;
    private String mStoreId;
    private long mBusinessId;
    private String mAddress;
    private String mSignboard;

    /**
     * @throws IllegalArgumentException
     */
    public OrderKit(String token, String externalId, long businessId,
                    String address, String signboard) throws IllegalArgumentException{

        checkArguments(token, externalId, businessId);

        mToken = token;
        mStoreId = externalId;
        mBusinessId = businessId;
        mAddress = address;
        mSignboard = signboard;
    }

    /**
     * Opens default ui module that allows you to make an order and send it to myOrder server
     */
    public void startOrdering(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, SkusActivity.class);
            intent.putExtra(SkusActivity.EXTRA_TOKEN, mToken);
            intent.putExtra(SkusActivity.EXTRA_STORE_ID, mStoreId);
            intent.putExtra(SkusActivity.EXTRA_BUSINESS_ID, mBusinessId);
            intent.putExtra(SkusActivity.EXTRA_ADDRESS, mAddress);
            intent.putExtra(SkusActivity.EXTRA_SIGNBOARD, mSignboard);
            context.startActivity(intent);
        }
    }

    /**
     * Synchronous execution
     * @return all skus related to business id
     */
    public List<Sku> getSkus() {
        return RepositoryFactory.getRepository().getSkus(mBusinessId, mToken);
    }

    public int getKitVersion() {
        return KitSettings.getKitVersion();
    }


    private void checkArguments(String token, String externalId, long businessId) throws IllegalArgumentException{
        if (GuavaSubstitute.isNullOrEmpty(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        if (GuavaSubstitute.isNullOrEmpty(externalId)) {
            throw new IllegalArgumentException("Invalid external_id");
        }

        if (businessId < 0) {
            throw new IllegalArgumentException("Invalid business_id");
        }
    }
}
