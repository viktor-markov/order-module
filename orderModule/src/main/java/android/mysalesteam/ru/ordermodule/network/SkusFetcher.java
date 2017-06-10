package android.mysalesteam.ru.ordermodule.network;

/**
 * Created by Alexey Nekrasov on 02.12.16.
 */

public class SkusFetcher extends NetworkGet {

    private long companyId;

    public SkusFetcher(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getUriSuffix() {
        return "/api/skus.json" + "?company_id=" + companyId;
    }
}
