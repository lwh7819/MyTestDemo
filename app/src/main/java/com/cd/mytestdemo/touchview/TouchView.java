package com.cd.mytestdemo.touchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TouchView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private SurfaceHolder surfaceHolder;
    private Canvas mCanvas;
    private boolean startDraw;

    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        // 设置获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);
    }

    private void draw() {
        try {
            mCanvas = surfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);

            mPaint.setStrokeWidth(10);
            mPaint.setColor(Color.BLACK);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                surfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();    //获取手指移动的x坐标
        int y = (int) event.getY();    //获取手指移动的y坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("lwh", "width= " + w + "\nheight= " + h + "\noldw= " + oldw + "\noldh= " + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDraw = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        startDraw = false;
    }

    @Override
    public void run() {
        while (startDraw) {
            Log.e("lwh", "i am running");
            draw();
        }
        Log.e("lwh", "i am running in thread");
    }

    public void reset() {
        mPaint.reset();
    }
}
