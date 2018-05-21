package com.cd.mytestdemo.testfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.base.BaseActivity;

/**
 * Created by lv.weihao on 2018/5/11.
 */
public class TestFragmentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = new Bundle();
        args.putString("text", "one");
        TestFragment1 testFragment1 = new TestFragment1();
        testFragment1.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_view, testFragment1, "fragment1");
        ft.commit();
    }
}
