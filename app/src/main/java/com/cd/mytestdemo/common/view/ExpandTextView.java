package com.cd.mytestdemo.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.*;
import android.util.AttributeSet;
import android.widget.TextView;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.excem.BetterHighlightSpan;
import com.cd.mytestdemo.excem.RadiusBackgroundSpan;

/**
 * 自定义TextView拓展
 */
public class ExpandTextView extends TextView {
    private Context mContext;
    private boolean mExpand = false;
    private String mExpandText;
    private String mExpandColor = "black";
    private String mExpandPosition = "left";
    private String mContent = "";
    private String mText = "";

    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView);
        mExpand = array.getBoolean(R.styleable.ExpandTextView_expand, false);
        mExpandText = array.getString(R.styleable.ExpandTextView_expand_text);
        mExpandColor = array.getString(R.styleable.ExpandTextView_expand_color);
        mExpandPosition = array.getString(R.styleable.ExpandTextView_expand_position);
        array.recycle();

        mText = getText().toString();

        if (mExpand && mExpandText != null) {
            if ("left".equals(mExpandPosition)) {
                mContent = "<font color='" + mExpandColor + "'>" + mExpandText +"</font>" + mText;
            } else {
                mContent = mText + "<font color='" + mExpandColor + "'>" + mExpandText +"</font>";
            }

            setText(Html.fromHtml(mContent));
        }
    }

    //设置必填"*"
    public void setRequiredText(String text) {
        String mRequired = "<font color= \"#FF0000\">" + "*" +"</font>" + text;
        setText(Html.fromHtml(mRequired));
    }

    //设置序列号
    public void setTitleNumber(String number, String text) {
        String mRequired = "      " + number + "." + text;
        setText(Html.fromHtml(mRequired));
    }

    //设置序列号
    public void setTitleNumber(String number, int text) {
        String mRequired = "\u3000\u3000" + number + "." + mContext.getString(text);
        setText(mRequired);
    }

    //设置序列号
    public void setTitleNumber(int number, int text, int type) {
        String typeText = "";
        int bgColor = Color.WHITE;
        switch (type) {
            case 1:
                typeText = "普通";
                bgColor = Color.parseColor("#FF00FF00");
                break;
            case 2:
                typeText = "一般";
                bgColor = Color.parseColor("#44acf4");
                break;
            case 3:
                typeText = "困难";
                bgColor = Color.parseColor("#99FF1533");
                break;
        }
        String str = "\u3000\u3000" + number + "." + typeText + " " + mContext.getString(text);
        int start = "\u3000\u3000".length() + String.valueOf(number).length() + 1;
        int end = start + typeText.length();
        SpannableString spanStr = new SpannableString(str);
        spanStr.setSpan(new RadiusBackgroundSpan(bgColor, 5), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanStr.setSpan(new RelativeSizeSpan (0.8f), start, end,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(spanStr);
    }

//    //设置序列号
//    public void setTitleNumber(String number, int text, String typeText) {
//        String mRequired = "\u3000\u3000" + number + "." +
//                "<font color= \"#FFFF00\"  style= \"background-color:#0000FF\">" + typeText +"</font>" +
//                mContext.getString(text);
//        setText(Html.fromHtml(mRequired));
//    }

    /**
     * 移除拓展加进的内容
     */
    public void removeExpand() {
        if (mExpand) {
            setText(mText);
        }
    }
}
