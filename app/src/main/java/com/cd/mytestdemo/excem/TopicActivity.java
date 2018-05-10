package com.cd.mytestdemo.excem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/5/9.
 */
public class TopicActivity extends AppCompatActivity {

    @BindView(R.id.content_view)
    LinearLayout contentView;
    private Topic topic;
    private Topic topic1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);


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
        topic = new Topic(this, true, topicObj, topicValueObj);
        contentView.addView(topic);

        topicObj.setNumber(2);
        topicObj.setTopicStr(R.string.topic1);
        topicObj.setType(2);
        array.clear();
        array.add("答案一你可以选");
        array.add("答案二你也可以选");
        array.add("答案三你也可以选");
        array.add("答案四你也可以选");
        array.add("答案五你也可以选");
        topic1 = new Topic(this, false, topicObj, topicValueObj);
        contentView.addView(topic1);
    }

    @OnClick(R.id.btn_get_result)
    public void onViewClicked() {
        String result = topic.getResult();
        String result1 = topic1.getResult();
        Log.e("lwh", "result_one:" + result);
        Log.e("lwh", "result_two:" + result1);
    }
}
