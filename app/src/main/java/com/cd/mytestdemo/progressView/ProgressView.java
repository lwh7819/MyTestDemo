package com.cd.mytestdemo.progressView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/6/29.
 */
public class ProgressView extends View {
    public static final  String colors[] = {"#3aa1ff", "#7dc0ff", "#5ddfcf", "#4ecb73", "#84e0a0", "#a9ea74",
            "#fbd437", "#eeb68d", "#5baf74", "#273777", "#778ce7", "#8879d1",
            "#975fe5", "#b58bf0", "#749fea", "#36cbcb", "#85e5e5", "#74b1ea",
            "#5254cf", "#9395ff", "#e294ea", "#f2637b", "#fb90a2", "#dba7df",
            "#6579ff", "#a3a1ff", "#88cdd8", "#7ab186", "#a9cfaf", "#c5de87",
            "#fcbd4a", "#e7a371", "#3aa1ff"};

    private Context context;
    private List<String> typeTitleList;

    private int outBarHeight = 40;
    private int centerBarHeight = 4;
    private int progressBarHeight = 2;
    private int progressColor = Color.BLUE;
    private int titleTextColor = Color.parseColor("#666666");
    private int progressTextColor = Color.parseColor("#666666");
    private int titleTextSize = 15;
    private int progressTextSize = 12;
    private int percent = 0;
    private String progressText = "0";
    private int postion;
    private ObjectAnimator objectAnimator;

    public ProgressView(Context context, List<String> typeTitleList) {
        super(context);
        this.context = context;
        this.typeTitleList = typeTitleList;
        if (typeTitleList == null) {
            typeTitleList = new ArrayList<>();
        }
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyProgressView);
        outBarHeight = array.getInteger(R.styleable.MyProgressView_outBarHeight, 40);
        centerBarHeight = array.getInteger(R.styleable.MyProgressView_centerBarHeight, 4);
        progressBarHeight = array.getInteger(R.styleable.MyProgressView_progressBarHeight, 2);
        progressColor = array.getColor(R.styleable.MyProgressView_progressColor, Color.BLUE);
        titleTextColor = array.getColor(R.styleable.MyProgressView_titleTextColor, Color.parseColor("#666666"));
        progressTextColor = array.getColor(R.styleable.MyProgressView_progressTextColor, Color.parseColor("#666666"));
        titleTextSize = array.getInteger(R.styleable.MyProgressView_titleTextSize, 15);
        progressTextSize = array.getInteger(R.styleable.MyProgressView_progressTextSize, 12);
        percent = array.getInteger(R.styleable.MyProgressView_percent, 0);
        progressText = array.getString(R.styleable.MyProgressView_progressText) == null ? "" : array.getString(R.styleable.MyProgressView_progressText);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (typeTitleList != null) {
            int screenWidth = canvas.getWidth() - canvas.getWidth() / 10;
            int screenHeight = canvas.getHeight();
            Log.e("lwh", "width:" + screenWidth + "height:" + screenHeight);

            int startLeft = 0;
            int startTop = 100;
            int startRight = screenWidth / getCount();
            int startBottom = startTop + dp2px(outBarHeight);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(colors[0]));

            /**
             * 绘制外层背景
             */
            RectF rectF = new RectF(startLeft, startTop, startRight, startBottom);
            RectF rectFAng = new RectF(startRight - (startBottom - startTop) / 2, startTop, startRight, startBottom);

            for (int i = 0; i < getCount(); i++) {
                int maxIndex = getCount() - 1;
                paint.setColor(Color.parseColor(colors[i]));
                if (i == 0) {
                    canvas.drawRoundRect(rectF, (startBottom - startTop) / 2, (startBottom - startTop) / 2, paint);
                    canvas.drawRect(rectFAng, paint);
                } else if (i == maxIndex) {
                    rectF.left = startRight * maxIndex;
                    rectF.right = startRight * (maxIndex + 1);
                    rectFAng.left = startRight * maxIndex;
                    rectFAng.right = startRight * maxIndex + (startBottom - startTop) / 2;
                    canvas.drawRoundRect(rectF, (startBottom - startTop) / 2, (startBottom - startTop) / 2, paint);
                    canvas.drawRect(rectFAng, paint);
                } else {
                    rectF.left = startRight * i;
                    rectF.right = startRight * (i + 1);
                    canvas.drawRect(rectF, paint);
                }
            }

            /**
             * 绘制内层背景
             */
            int centerHeight = dp2px(centerBarHeight);
            paint.setColor(Color.WHITE);
            RectF rectFCenterWhite = new RectF(startLeft + (startBottom - startTop) / 3, startTop + (startBottom - startTop) / 2 - centerHeight / 2,
                    startRight * getCount() - (startBottom - startTop) / 3, startTop + (startBottom - startTop) / 2 + centerHeight / 2);
            canvas.drawRoundRect(rectFCenterWhite, centerHeight / 2, centerHeight / 2, paint);

        /*
        * 绘制进度
        */
            float percentD = percent / 100f;
            int progressHeight = dp2px(progressBarHeight);
            int end = (int)(((startBottom - startTop) / 3) +
                    ((screenWidth - (startBottom - startTop) * 2 / 3) * percentD));
            if (objectAnimator == null) {
                doAnimater(startLeft + (startBottom - startTop) / 3 + progressHeight / 2, end);
            }
            Log.e("lwhh", postion + "");
            paint.setColor(progressColor);
            Rect rectProgress = new Rect(startLeft + (startBottom - startTop) / 3 + progressHeight / 2, startTop + (startBottom - startTop) / 2 - progressHeight / 2,
                    postion, startTop + (startBottom - startTop) / 2 + progressHeight / 2);
            canvas.drawRect(rectProgress, paint);

            /**
             * 绘制下标文字
             */
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(titleTextColor);
            textPaint.setTextSize(sp2px(titleTextSize));
            StaticLayout sl;
            for (int j = 0; j < getCount(); j++) {
                int height = startBottom + dp2px(8);
                int width = 0 + dp2px(4);
                if (j != 0) {
                    height = 0;
                    width = startRight;
                }
                canvas.save();
                sl = new StaticLayout(getTitle(j), textPaint, startRight - dp2px(4), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
                canvas.translate(width, height);
                sl.draw(canvas);
            }

            canvas.translate(-(dp2px(4) + startRight * (getCount() - 1)), - (startBottom + dp2px(8)));
            canvas.translate(postion - dp2px(5), dp2px(5));
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_porper),
                    0, 0, paint);

            textPaint.setTextSize(sp2px(progressTextSize));
            textPaint.setColor(progressTextColor);
            sl = new StaticLayout(progressText, textPaint, dp2px(40), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
            canvas.translate(dp2px(2), dp2px(5));
            sl.draw(canvas);
        }
    }

    public void setOutBarHeight(int outBarHeight) {
        this.outBarHeight = outBarHeight;
    }

    public void setCenterBarHeight(int centerBarHeight) {
        this.centerBarHeight = centerBarHeight;
    }

    public void setProgressBarHeight(int progressBarHeight) {
        this.progressBarHeight = progressBarHeight;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public void setProgressTextColor(int progressTextColor) {
        this.progressTextColor = progressTextColor;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public void setProgressTextSize(int progressTextSize) {
        this.progressTextSize = progressTextSize;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public void setTypeTitleList(List<String> typeTitleList) {
        this.typeTitleList = typeTitleList;
    }

    public int getCount() {
        return typeTitleList.size();
    }

    public String getTitle(int position) {
        return typeTitleList.get(position);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //适配wrap_content
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            float textWidth = 1080;   //文本的宽度
            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float textHeight = dp2px(150);
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    /**
     * 将dp转换为px
     */
    private int dp2px(float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * 将sp转换为px
     */
    public int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void doAnimater(float start, float end) {
        objectAnimator = ObjectAnimator.ofFloat(this, "mPercent", start, end);
        objectAnimator.setDuration(1000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setHeightNotInUiThread((int) value);
            }
        });
        objectAnimator.start();
    }

    public void setHeightNotInUiThread(int height) {
        this.postion = height;
        Log.e("lwh", "postion:" + postion);
        this.postInvalidate();
    }
}
