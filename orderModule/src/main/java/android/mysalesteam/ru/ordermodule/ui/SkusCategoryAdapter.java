package android.mysalesteam.ru.ordermodule.ui;

import android.mysalesteam.ru.ordermodule.R;
import android.mysalesteam.ru.ordermodule.models.SkuCategory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexn on 01.12.2016.
 */

class SkusCategoryAdapter extends RecyclerView.Adapter<SkusCategoryAdapter.ViewHolder> {

    private List<SkuCategory> categories = new ArrayList<>();
    private OnCategoryClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_line_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setData(@NonNull List<SkuCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    protected void setOnCategoryClickListener(@NonNull OnCategoryClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onCategoryClick(categories.get(getAdapterPosition()));
                    }
                }
            });
       }

        void bind(SkuCategory category) {
            name.setText(category.getName());
        }
    }

    interface OnCategoryClickListener {
        void onCategoryClick(SkuCategory category);
    }
}
