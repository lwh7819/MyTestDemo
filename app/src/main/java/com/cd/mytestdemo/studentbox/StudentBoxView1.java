package com.cd.mytestdemo.studentbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class StudentBoxView1 extends android.support.v7.widget.AppCompatTextView {
    private Paint mPaint;

    public StudentBoxView1(Context context) {
        this(context, null);
    }

    public StudentBoxView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StudentBoxView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0 , 200, 100, mPaint);
        super.onDraw(canvas);
    }
}
