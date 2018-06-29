package com.cd.mytestdemo.progressView;

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

        mProgressView.setTypeTitleList(mList);
        mProgressView.invalidate();
        mProgressView2.setTypeTitleList(mList);
        mProgressView2.invalidate();

    }
}
