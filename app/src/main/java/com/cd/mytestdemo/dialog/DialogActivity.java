package com.cd.mytestdemo.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cd.mytestdemo.R;

/**
 * Created by lv.weihao on 2018/5/21.
 */
public class DialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.m_btn_close)
    public void onViewClicked() {
        new CodeDialog(this).show();
    }
}
