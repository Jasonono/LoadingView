package com.jason.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

    private Paint mPaint;
    private int mColor;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int r = width / 2;
        int cx = width / 2;
        int cy = height / 2;

        canvas.drawCircle(cx,cy,r,mPaint);
    }

    public void exChangeColor(int color){
        mPaint.setColor(color);
        mColor = color;
        invalidate();
    }

    public int getColor() {
        return mColor;
    }
}
