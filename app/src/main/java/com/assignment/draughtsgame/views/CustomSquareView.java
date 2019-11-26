package com.assignment.draughtsgame.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.GridView;

public class CustomSquareView extends GridView {
    private static final float RATIO = 4f / 3f;

    public CustomSquareView(Context context) {
        super(context);
    }

    public CustomSquareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomSquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // here am returning the width in place of height, so width = height
    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        p.setColor(Color.BLACK);
        canvas.drawRect(0, 0, getMeasuredWidth() - 1, getMeasuredHeight() - 1, p);
    }

}
