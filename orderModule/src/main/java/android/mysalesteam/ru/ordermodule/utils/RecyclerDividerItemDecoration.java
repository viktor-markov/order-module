package android.mysalesteam.ru.ordermodule.utils;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.mysalesteam.ru.ordermodule.R;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by Alexey Nekrasov on 15.11.16.
 */

public class RecyclerDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int dividerHeight;

    public RecyclerDividerItemDecoration(@NonNull View view) {
        mDivider = ContextCompat.getDrawable(view.getContext(), R.drawable.recycler_divider);
        dividerHeight = (int) view.getContext().getResources().getDimension(R.dimen.divider_height);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + dividerHeight;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
