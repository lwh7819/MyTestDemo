package com.cd.mytestdemo.excem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.ExpandTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/5/9.
 */
public class Topic extends View {
    private static String[] mBook = {"A", "B", "C", "D", "E", "F", "G"};
    private Context mContext;
    private int number;
    private int topicStr;
    private int type;
    private List<String> topicValueList = new ArrayList<>();

    public Topic(Context context, TopicObj topicObj, TopicValueObj topicValueObj) {
        super(context);
        this.mContext = context;
        this.number = topicObj.getNumber();
        this.topicStr = topicObj.getTopicStr();
        this.type = topicObj.getType();
        this.topicValueList = topicValueObj.getValueList();
        init();
    }

    private void init() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.topic, null);
        ExpandTextView mTopic = v.findViewById(R.id.m_topic_str);
        mTopic.setTitleNumber(1, R.string.topic1, 1);

        LinearLayout mContentView = v.findViewById(R.id.m_content);
        for (int i = 0; i < topicValueList.size(); i++) {
            View valueView = LayoutInflater.from(mContext).inflate(R.layout.topic_item, null);
            TextView mNumView = valueView.findViewById(R.id.m_tv_ABC);
            TextView mValueView = valueView.findViewById(R.id.m_tv_value);
            mNumView.setText(mBook[i]);
            mValueView.setText(topicValueList.get(i));
            mContentView.addView(valueView);
        }
    }
}
