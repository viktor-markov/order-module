package android.mysalesteam.ru.ordermodule.network;


import android.mysalesteam.ru.ordermodule.models.Draft;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Alexey Nekrasov on 08.12.16.
 */

public class OrderPostFetcher extends NetworkPost {

    private Draft draft;

    public OrderPostFetcher(Draft draft) {
        this.draft = draft;
    }

    @Override
    protected RequestBody getEntity() throws IOException {
        String jsonString = draft.toJsonString();

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON, "{\"order\":" + jsonString + "}");
    }

    @Override
    public String getUriSuffix() {
        return "/api/orders.json";
    }

  /*  @Override
    public void checkStatusCode(Response response) throws IOException, SessionExpiredException, PermissionError {
        super.checkStatusCode(response);
    }*/
}
