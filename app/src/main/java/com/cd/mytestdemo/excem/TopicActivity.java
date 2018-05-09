package com.cd.mytestdemo.excem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.ExpandTextView;

/**
 * Created by lv.weihao on 2018/5/9.
 */
public class TopicActivity extends AppCompatActivity {
    @BindView(R.id.m_topic_str)
    ExpandTextView mTopicStr;
    @BindView(R.id.m_topic_str2)
    ExpandTextView mTopicStr2;
    @BindView(R.id.m_topic_str3)
    ExpandTextView mTopicStr3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);
        ButterKnife.bind(this);

        mTopicStr.setTitleNumber(1, R.string.topic1, 1);
        mTopicStr2.setTitleNumber(2, R.string.topic1, 2);
        mTopicStr3.setTitleNumber(3, R.string.topic1, 3);
    }
}
