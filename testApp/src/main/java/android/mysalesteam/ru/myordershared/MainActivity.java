package android.mysalesteam.ru.myordershared;

import android.mysalesteam.ru.ordermodule.OrderKit;
import android.mysalesteam.ru.ordermodule.models.Sku;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private OrderKit mKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initModule();
    }

    private void initModule() {
        try {
            mKit =  new OrderKit("dccd44692eaa38c161081b3b4db04f11", "external id", 5, "address", "signboard");
        } catch (IllegalArgumentException e) {
            String error = e.getMessage();
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
            mTextView.setText(error);
        }
    }

    private void initViews() {
        mTextView = (TextView) findViewById(R.id.tv_skus);

        findViewById(R.id.btn_ordering).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKit.startOrdering(MainActivity.this);
            }
        });

        findViewById(R.id.btn_sku).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TestTask().execute();
            }
        });
    }


    private class TestTask extends AsyncTask<Void, Void, List<Sku>> {

        @Override
        protected void onPostExecute(List<Sku> data) {
            super.onPostExecute(data);
            if (data != null) {
                String message = "Skus loaded, size: " + data.size();
                mTextView.setText(message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            } else {
                mTextView.setText("Error");
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected List<Sku> doInBackground(Void... voids) {
            return mKit.getSkus();
        }

    }
}
