package com.example.vladislav.androidstudy.jobs.currency;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Sets offset for a recyclerview
 *
 * Created by Влад on 06.06.2018.
 */

public class CurrencyItemOffsetDecoration extends DividerItemDecoration {

    private int mMarginTop;
    private int mMarginBottom;
    private int mMarginLeft;
    private int mMarginRight;

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context     Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public CurrencyItemOffsetDecoration(Context context, int orientation) {
        super(context, orientation);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) == 0) {
            outRect.set(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);
        } else {
            outRect.set(mMarginLeft, 0, mMarginRight, mMarginBottom);
        }
    }

    public void setItemMargins(int marginTop, int marginLeft, int marginBottom, int marginRight) {
        mMarginTop = marginTop;
        mMarginLeft = marginLeft;
        mMarginBottom = marginBottom;
        mMarginRight = marginRight;
    }

}
