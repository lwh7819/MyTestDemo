package com.cd.mytestdemo.cardview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cd.mytestdemo.R;

/**
 * Created by lv.weihao on 2018/5/10.
 */
public class CardViewActivity extends AppCompatActivity {
    @BindView(R.id.m_edt_user_name)
    AddAndSubEditText mEdtUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        ButterKnife.bind(this);

        mEdtUserName.setOnDrawableRightListener(new AddAndSubEditText.OnDrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                Log.e("lwh", "getClicked");
            }
        });
    }
}
