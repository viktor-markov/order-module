package android.mysalesteam.ru.ordermodule.ui;

import android.content.Context;
import android.mysalesteam.ru.ordermodule.utils.KeyboardHelper;
import android.mysalesteam.ru.ordermodule.utils.PriceFormatter;
import android.mysalesteam.ru.ordermodule.R;
import android.mysalesteam.ru.ordermodule.models.Sku;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexn on 01.12.2016.
 */

class SkusAdapter extends RecyclerView.Adapter<SkusAdapter.ViewHolder> {

    private List<Sku> skus = new ArrayList<>();
    private Context context;
    private OnSkuQuantityChangeListener listener;
    private OnItemClickListener mClickListener;

    protected interface OnItemClickListener {
        boolean onSKUCountClick();

        void onItemClick(int position);
    }

    protected SkusAdapter(Context context, @NonNull OnSkuQuantityChangeListener listener) {
        this.context = context;
        this.listener = listener;
    }

    protected void setOnClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sku_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    protected Sku getItem(int position) {
        return skus.get(position);
    }

    @Override
    public int getItemCount() {
        return skus.size();
    }

    public void setData(@NonNull List<Sku> skus) {
        this.skus = skus;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        EditText countEdit;
        TextView unit;
        View priceLayout;

        ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.sku_name);
            price = (TextView) itemView.findViewById(R.id.sku_price);
            countEdit = (EditText) itemView.findViewById(R.id.order_sku_count);
            unit = (TextView) itemView.findViewById(R.id.sku_unit);
            priceLayout = itemView.findViewById(R.id.price_layout);


            countEdit.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return priceLayout.onTouchEvent(motionEvent);
                }
            });

            priceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener.onSKUCountClick()) {
                        countEdit.requestFocus();
                        countEdit.setSelection(countEdit.length());

                        KeyboardHelper.showKeyboard(context, countEdit);
                    }

                }
            });

            final TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String countStr = s.toString();
                    getItem(getAdapterPosition()).setQuantity(countStr.isEmpty() ? 0 : Integer.valueOf(countStr));
                    listener.onSkuQuantityChanged(getItem(getAdapterPosition()));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };

            countEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        KeyboardHelper.hideKeyboard(context, countEdit);
                        countEdit.clearFocus();
                    }
                    return false;
                }
            });

            countEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (hasFocus) {
                        countEdit.setHint(R.string.count_hint);
                        unit.setVisibility(View.GONE);
                        countEdit.addTextChangedListener(watcher);
                      //  KeyboardHelper.showKeyboard(context, countEdit);

                    } else {
                        countEdit.removeTextChangedListener(watcher);
                        String text = countEdit.getText().toString().trim();
                        int c = text.isEmpty() ? 0 : Integer.valueOf(text);

                        if (c == 0) {
                            countEdit.setText(null);
                        } else {
                            countEdit.setHint(null);
                            countEdit.setText(String.valueOf(c));
                            unit.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        void bind(Sku sku) {
            name.setText(sku.getName());
            price.setText(PriceFormatter.getInstance(context).format(sku.getPrice()));

            if (sku.getQuantity() == 0) {
                countEdit.setHint(R.string.count_hint);
                countEdit.setText(null);
                unit.setVisibility(View.GONE);
            } else {
                countEdit.setHint(null);
                countEdit.setText(String.valueOf(sku.getQuantity()));
                unit.setVisibility(View.VISIBLE);
            }
        }
    }

    protected interface OnSkuQuantityChangeListener {
        void onSkuQuantityChanged(Sku sku);
    }
}
