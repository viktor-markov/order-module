package android.mysalesteam.ru.ordermodule.ui;

import android.mysalesteam.ru.ordermodule.utils.KeyboardHelper;
import android.mysalesteam.ru.ordermodule.utils.PresenterManager;
import android.mysalesteam.ru.ordermodule.R;
import android.mysalesteam.ru.ordermodule.utils.RecyclerDividerItemDecoration;
import android.mysalesteam.ru.ordermodule.models.Sku;
import android.mysalesteam.ru.ordermodule.models.SkuCategory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SkusActivity extends AppCompatActivity implements SkusView,
        SkusCategoryAdapter.OnCategoryClickListener,
        SkusAdapter.OnSkuQuantityChangeListener,
        SkusAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener{

    public static final String EXTRA_TOKEN = "token";
    public static final String EXTRA_STORE_ID = "store_id";
    public static final String EXTRA_BUSINESS_ID = "business_id";
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_SIGNBOARD = "signboard";



    private SkusPresenter mPresenter;

    private SkusAdapter mSkusAdapter;
    private SkusCategoryAdapter mCategoryAdapter;
    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mRefreshLayout;
    private TextView mtvEmpty;


    @Override
    public void setSkusData(SkuCategory data) {
        if (mSkusAdapter != null) {
               showSkus(data);
        }
    }

    @Override
    public void setCategoryData(List<SkuCategory> data) {
        if (mCategoryAdapter != null) {
            showCategories(data);
        }
    }

    @Override
    public void hideProgress() {
        if (mRefreshLayout != null) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void showProgress() {
        if (mRefreshLayout != null) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void onCategoryClick(SkuCategory category) {
        showSkus(category);
    }

    @Override
    public void onSkuQuantityChanged(Sku sku) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onSKUCountClick() {
        return true;
    }

    @Override
    public void onRefresh() {
        mPresenter.loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sku);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        if (mPresenter == null) {
            setTitle("Заказ");

            String token = "";
            String storeId = "";
            long businessId = -1;
            String address = "";
            String signboard = "";

            if (getIntent() != null && getIntent().getExtras() != null) {
                token = getIntent().getStringExtra(EXTRA_TOKEN);
                storeId = getIntent().getStringExtra(EXTRA_STORE_ID);
                businessId = getIntent().getLongExtra(EXTRA_BUSINESS_ID, -1);
                address = getIntent().getStringExtra(EXTRA_ADDRESS);
                signboard = getIntent().getStringExtra(EXTRA_SIGNBOARD);
            }
            mPresenter = new SkusPresenter(token, storeId, businessId);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mtvEmpty = (TextView) findViewById(R.id.empty);


        mSkusAdapter = new SkusAdapter(this, this);
        mCategoryAdapter = new SkusCategoryAdapter();
        mCategoryAdapter.setOnCategoryClickListener(this);
        mSkusAdapter.setOnClickListener(this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerDividerItemDecoration(mRecyclerView));


        mRefreshLayout.setOnRefreshListener(this);

     }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unbindView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }


    private void showCategories(List<SkuCategory> data) {
        mPresenter.setCurrentCategory(null);
        mPresenter.setScreenMode(SkusPresenter.ScreenMode.MODE_CATEGORY);
        setTitle("Заказ");

        mtvEmpty.setVisibility(View.GONE);
        mCategoryAdapter.setData(data);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void showSkus(SkuCategory category) {
        mPresenter.setCurrentCategory(category);
        mPresenter.setScreenMode(SkusPresenter.ScreenMode.MODE_SKU);
        setTitle(category.getName());
        if (category.getSkus().isEmpty()) {
            showEmpty();
        } else {
            mSkusAdapter.setData(category.getSkus());
            mRecyclerView.setAdapter(mSkusAdapter);
            mtvEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mtvEmpty.setText(R.string.no_data_to_display);
        mtvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mtvEmpty.setText(R.string.loading_error);
        mtvEmpty.setVisibility(View.VISIBLE);
    }


    private void resetFocus() {
        mRecyclerView.clearFocus();
        KeyboardHelper.hideKeyboard(this, mRecyclerView);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mPresenter.getScreenMode() == SkusPresenter.ScreenMode.MODE_CATEGORY) {
            finish();
        } else {
            resetFocus();
            showCategories(mPresenter.getCategoryData());
        }
    }
}
