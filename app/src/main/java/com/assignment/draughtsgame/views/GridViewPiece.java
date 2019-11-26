package com.assignment.draughtsgame.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class GridViewPiece extends AppCompatImageView {
    public GridViewPiece(Context context) {
        super(context);
    }

    public GridViewPiece(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewPiece(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
