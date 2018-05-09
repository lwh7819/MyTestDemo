package com.cd.mytestdemo.excem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lv.weihao on 2018/5/9.
 */
public class TopicActivity extends AppCompatActivity {

    @BindView(R.id.content_view)
    FrameLayout contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

//        mTopicStr.setTitleNumber(1, R.string.topic1, 1);
//        mTopicStr2.setTitleNumber(2, R.string.topic1, 2);
//        mTopicStr3.setTitleNumber(3, R.string.topic1, 3);

        TopicObj topicObj = new TopicObj();
        topicObj.setNumber(1);
        topicObj.setTopicStr(R.string.topic1);
        topicObj.setType(1);

        TopicValueObj topicValueObj = new TopicValueObj();
        List<String> array = new ArrayList<>();
        array.add("正确");
        array.add("错误");
        array.add("中立");
        topicValueObj.setValueList(array);
        Topic topic = new Topic(this, topicObj, topicValueObj);
        contentView.addView(topic);

    }
}
