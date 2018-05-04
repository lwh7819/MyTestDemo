package com.cd.mytestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cd.mytestdemo.glide.GlideTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.entry_1, R.id.entry_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_1:
                startActivity(new Intent(this, GlideTestActivity.class));
                break;
            case R.id.entry_2:
                break;
        }
    }
}
