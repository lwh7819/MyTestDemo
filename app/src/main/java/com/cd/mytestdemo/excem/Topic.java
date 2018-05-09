package com.cd.mytestdemo.excem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.ExpandTextView;

/**
 * Created by lv.weihao on 2018/5/9.
 */
public class Topic extends View {
    private Context mContext;
    private int number;
    private int topicStr;
    private int type;

    public Topic(Context context, TopicObj topicObj) {
        super(context);
        this.mContext = context;
        this.number = topicObj.getNumber();
        this.topicStr = topicObj.getTopicStr();
        this.type = topicObj.getType();
        init();
    }

    private void init() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.topic, null);
        ExpandTextView mTopic = v.findViewById(R.id.m_topic_str);
        mTopic.setTitleNumber(1, R.string.topic1, 1);
    }
}
