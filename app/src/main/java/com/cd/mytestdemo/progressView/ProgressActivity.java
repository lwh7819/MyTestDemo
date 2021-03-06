package com.cd.mytestdemo.progressView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/6/29.
 */
public class ProgressActivity extends AppCompatActivity {
    @BindView(R.id.content_view)
    LinearLayout contentView;
    @BindView(R.id.m_progress_view)
    ProgressView mProgressView;
    @BindView(R.id.m_progress_view2)
    ProgressView mProgressView2;
    @BindView(R.id.m_progress_view3)
    ProgressViewRect mProgressView3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);

        List<String> mList = new ArrayList<String>();
        mList.add("过轻");
        mList.add("正常");
        mList.add("过重");
        mList.add("肥胖");
        mList.add("非常非常肥胖");

        List<String> mList2 = new ArrayList<String>();
        mList2.add("过轻");
        mList2.add("正常");
        mList2.add("过重");
        mList2.add("肥胖");
        mList2.add("非常非常肥胖");
        mList2.add("非常非常非常的肥胖");

        mProgressView.setTypeTitleList(mList);
        mProgressView.setPercent(90);
        mProgressView.setProgressColor(Color.RED);
        mProgressView2.setTypeTitleList(mList2);
        mProgressView2.setPercent(50);
        mProgressView2.setProgressBarHeight(1);
        mProgressView2.setProgressText("27.63");

        mProgressView3.setProgressText("27.63");
        mProgressView3.setTypeTitleList(mList2);

//        ProgressViewRect progressViewRect = new ProgressViewRect(this, mList2);
//        progressViewRect.setPercent(100);
//        progressViewRect.setProgressText("60/180");
//        contentView.addView(progressViewRect);
    }
}
