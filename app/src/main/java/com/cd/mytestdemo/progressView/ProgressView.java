package com.cd.mytestdemo.progressView;

import android.content.Context;
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
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (typeTitleList != null) {
            int screenWidth = canvas.getWidth();
            int screenHeight = canvas.getHeight();
            Log.e("lwh", "width:" + screenWidth + "height:" + screenHeight);

            int startLeft = 0;
            int startTop = 0;
            int startRight = screenWidth / getCount();
            int startBottom = 100;

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(colors[0]));
            canvas.drawColor(Color.RED);

            /**
             * 绘制外层背景
             */
            RectF rectF = new RectF(startLeft, startTop, startRight, startBottom);
            RectF rectFAng = new RectF(startRight - startBottom / 2, startTop, startRight, startBottom);

            for (int i = 0; i < getCount(); i++) {
                int maxIndex = getCount() - 1;
                paint.setColor(Color.parseColor(colors[i]));
                if (i == 0) {
                    canvas.drawRoundRect(rectF, startBottom / 2, startBottom / 2, paint);
                    canvas.drawRect(rectFAng, paint);
                } else if (i == maxIndex) {
                    rectF.left = startRight * maxIndex;
                    rectF.right = startRight * (maxIndex + 1);
                    rectFAng.left = startRight * maxIndex;
                    rectFAng.right = startRight * maxIndex + startBottom / 2;
                    canvas.drawRoundRect(rectF, startBottom / 2, startBottom / 2, paint);
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
            int centerHeight = startBottom / 10;
            paint.setColor(Color.WHITE);
            RectF rectFCenterWhite = new RectF(startLeft + startBottom / 3, startTop + startBottom / 2 - centerHeight / 2,
                    startRight * getCount() - startBottom / 3, startTop + startBottom / 2 + centerHeight / 2);
            canvas.drawRoundRect(rectFCenterWhite, centerHeight / 2, centerHeight / 2, paint);

        /*
        * 绘制进度
        */
            int progressHeight = startBottom / 20;
            paint.setColor(Color.parseColor(colors[10]));
            Rect rectProgress = new Rect(startLeft + startBottom / 3 + progressHeight / 2, startTop + startBottom / 2 - progressHeight / 2,
                    startRight * 2, startTop + startBottom / 2 + progressHeight / 2);
            canvas.drawRect(rectProgress, paint);

            /**
             * 绘制下标文字
             */
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.parseColor("#999999"));
            textPaint.setTextSize(sp2px(15));
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

            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_porper),
                    0, 0, paint);
        }
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
            float textHeight = dp2px(100);
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

}
