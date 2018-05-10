package com.cd.mytestdemo.excem;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.ExpandTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/5/9.
 */
public class Topic extends LinearLayout {
    public static String[] mBook = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
    private Context mContext;
    private int number;
    private int topicStr;
    private int type;
    private List<String> topicValueList = new ArrayList<>();
    private boolean isSingle;

    private List<View> viewList = new ArrayList<>();
    private int topMargin;

    public Topic(Context context, boolean isSingle, TopicObj topicObj, TopicValueObj topicValueObj) {
        super(context);
        this.mContext = context;
        this.number = topicObj.getNumber();
        this.topicStr = topicObj.getTopicStr();
        this.type = topicObj.getType();
        this.topicValueList = topicValueObj.getValueList();
        this.isSingle = isSingle;
        init();
    }

    private void init() {
        View v = inflate(mContext, R.layout.topic, this);
        ExpandTextView mTopic = v.findViewById(R.id.m_topic_str);
        mTopic.setTitleNumber(number, topicStr, type);

        LinearLayout mContentView = v.findViewById(R.id.m_content);
        for (int i = 0; i < topicValueList.size(); i++) {
            View valueView = LayoutInflater.from(mContext).inflate(R.layout.topic_item, null);
            CheckBox mNumView = valueView.findViewById(R.id.m_tv_ABC);
            mNumView.setClickable(false);
            TextView mValueView = valueView.findViewById(R.id.m_tv_value);
            try {
                mNumView.setText(mBook[i]);
            } catch (Exception e) {
                Log.e("lwh", "book is only suppot 11");
            }
            mValueView.setText(topicValueList.get(i));
            if (!isSingle) {
                clickListener(valueView, mNumView, mValueView);
            }
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            topMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.common_top_margin);
            layoutParams.topMargin = topMargin;
            viewList.add(valueView);
            mContentView.addView(valueView, layoutParams);
        }
        if (isSingle) {
            clickSingleListener();
        }
    }

    private void clickSingleListener() {
       for (int i = 0; i < viewList.size(); i++) {
           final int index = i;
           View v = viewList.get(i);
           final CheckBox mNumView = v.findViewById(R.id.m_tv_ABC);
           final TextView mValueView = v.findViewById(R.id.m_tv_value);
           v.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (!mNumView.isChecked()) {
                       mNumView.setChecked(true);
                       mValueView.setBackground(mContext.getResources().getDrawable(R.drawable.topic_value_bg_select));
                       mValueView.setPadding(topMargin, 0, 0, 0);

                       for (int j = 0; j < viewList.size(); j++) {
                           View v1 = viewList.get(j);
                           CheckBox mNumView1 = v1.findViewById(R.id.m_tv_ABC);
                           TextView mValueView1 = v1.findViewById(R.id.m_tv_value);
                           if (index != j && mNumView1.isChecked()) {
                               mNumView1.setChecked(false);
                               mValueView1.setBackground(mContext.getResources().getDrawable(R.drawable.topic_value_bg));
                               mValueView1.setPadding(topMargin, 0, 0, 0);
                           }
                       }
                   } else {
                       mNumView.setChecked(false);
                       mValueView.setBackground(mContext.getResources().getDrawable(R.drawable.topic_value_bg));
                       mValueView.setPadding(topMargin, 0, 0, 0);
                   }
               }
           });
       }
    }

    private void clickListener(View v, final CheckBox mNumView, final TextView mValueView) {
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNumView.isChecked()) {
                    mNumView.setChecked(true);
                    mValueView.setBackground(mContext.getResources().getDrawable(R.drawable.topic_value_bg_select));
                    mValueView.setPadding(topMargin, 0, 0, 0);
                } else {
                    mNumView.setChecked(false);
                    mValueView.setBackground(mContext.getResources().getDrawable(R.drawable.topic_value_bg));
                    mValueView.setPadding(topMargin, 0, 0, 0);
                }
            }
        });
    }

    public String getResult() {
        StringBuffer sb = new StringBuffer();
        for (View v : viewList) {
            CheckBox mNumView = v.findViewById(R.id.m_tv_ABC);
            sb.append(mNumView.isChecked() ? "1," : "0,");
        }
        return sb.toString();
    }
}
