package com.cd.mytestdemo.towEnResult;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cd.mytestdemo.R;

/**
 * Created by lv.weihao on 2018/6/5.
 */
public class TowEnResultActivity extends AppCompatActivity {
    @BindView(R.id.m_edt)
    EditText mEdt;
    @BindView(R.id.m_tv)
    TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_e_n);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.m_btn)
    public void onViewClicked() {
        try {
            if (Long.parseLong(mEdt.getText().toString()) < 0) {
                mTv.setText("result is more than maxInt");
            } else {
                mTv.setText(getResult(Long.parseLong(mEdt.getText().toString())) + "");
            }
        } catch (Exception e) {
            Toast.makeText(this, "输入的值超过long最大值", Toast.LENGTH_LONG).show();
        }
    }

    private long getResult(long i) {
        i--;

        i |= i >>> 1;
        i |= i >>> 2;
        i |= i >>> 4;
        i |= i >>> 8;
        i |= i >>> 16;
        i |= i >>> 32;
        i |= i >>> 64;
        i |= i >>> 128;

        return i + 1;
    }
}
