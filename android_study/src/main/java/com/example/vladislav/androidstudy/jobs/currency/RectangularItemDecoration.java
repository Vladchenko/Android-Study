package com.example.vladislav.androidstudy.jobs.currency;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.vladislav.androidstudy.R;

/**
 * Sets offsets and round rectangle for item
 *
 * Created by Влад on 06.06.2018.
 */

public class RectangularItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaintCyan;
    private int mOffset;
    private Bitmap mBitmap;
    private Rect mRectScr;
    private Context mContext;

    public RectangularItemDecoration(Context context) {
        mOffset = 10;
        mContext = context;
        mPaintCyan = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContext.getResources().getColor(R.color.colorPrimaryDark);
        mPaintCyan.setColor(Color.DKGRAY);
        mPaintCyan.setStyle(Paint.Style.STROKE);
        mPaintCyan.setStrokeWidth(3);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mOffset, mOffset, mOffset, mOffset);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            c.drawRoundRect(layoutManager.getDecoratedLeft(child) + mOffset,
                    layoutManager.getDecoratedTop(child) + mOffset,
                    layoutManager.getDecoratedRight(child) - mOffset,
                    layoutManager.getDecoratedBottom(child) - mOffset, 15, 15, mPaintCyan);

        }
    }
}