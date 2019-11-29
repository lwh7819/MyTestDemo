package com.cd.mytestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cd.mytestdemo.animate.AnimateActivity;
import com.cd.mytestdemo.bluetooth.BluetoothActivity;
import com.cd.mytestdemo.cache.ImageActivity;
import com.cd.mytestdemo.cardview.CardViewActivity;
import com.cd.mytestdemo.dialog.DialogActivity;
import com.cd.mytestdemo.excem.TopicActivity;
import com.cd.mytestdemo.exoplayer.ExoPlayerActivity;
import com.cd.mytestdemo.glide.GlideTestActivity;
import com.cd.mytestdemo.hook.HookClick;
import com.cd.mytestdemo.hook.HookViewClickUtil;
import com.cd.mytestdemo.hook.RealClickFactory;
import com.cd.mytestdemo.progressView.ProgressActivity;
import com.cd.mytestdemo.savelogcat.SaveCustomLogcat;
import com.cd.mytestdemo.studentbox.StudentBoxActivity;
import com.cd.mytestdemo.tbs.TbsActivity;
import com.cd.mytestdemo.testfragment.TestFragmentActivity;
import com.cd.mytestdemo.textureview.TextureViewActivity;
import com.cd.mytestdemo.touchview.TouchViewActivity;
import com.cd.mytestdemo.towEnResult.TowEnResultActivity;
import com.cd.mytestdemo.viewpage.ViewPageActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @HookClick(value = R.id.entry_1, type = RealClickFactory.FAST_DOUBLE_CLICK_ENTRY_TYPE)
    private Button entry1;

    @HookClick(value = R.id.entry_2, type = RealClickFactory.FAST_DOUBLE_CLICK_IGNORE_TYPE)
    private Button entry2;

    @HookClick(value = R.id.entry_3, type = RealClickFactory.OTHER_CLICK_IGNORE_TYPE)
    private Button entry3;
    private static Map<Integer, Class> classMap = new HashMap<>();

    static {
        classMap.put(R.id.entry_1, GlideTestActivity.class);
        classMap.put(R.id.entry_2, CardViewActivity.class);
        classMap.put(R.id.entry_3, TopicActivity.class);
        classMap.put(R.id.entry_4, TestFragmentActivity.class);
        classMap.put(R.id.entry_5, BluetoothActivity.class);
        classMap.put(R.id.entry_6, DialogActivity.class);
        classMap.put(R.id.entry_7, ViewPageActivity.class);
        classMap.put(R.id.entry_8, TowEnResultActivity.class);
        classMap.put(R.id.entry_9, TbsActivity.class);
        classMap.put(R.id.entry_10, ProgressActivity.class);
        classMap.put(R.id.entry_11, ImageActivity.class);
        classMap.put(R.id.entry_12, TouchViewActivity.class);
        classMap.put(R.id.entry_13, StudentBoxActivity.class);
        classMap.put(R.id.entry_14, AnimateActivity.class);
        classMap.put(R.id.entry_15, TextureViewActivity.class);
        classMap.put(R.id.entry_16, ExoPlayerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                HookViewClickUtil.hookViews(MainActivity.this);
            }
        });

        SaveCustomLogcat.getInstance().saveLog("",  "id: 1\nname: lvweihao" + "\nerrorMessage: load fail");
    }

    @OnClick({R.id.entry_1, R.id.entry_2, R.id.entry_3, R.id.entry_4, R.id.entry_5, R.id.entry_6, R.id.entry_7
            , R.id.entry_8, R.id.entry_9, R.id.entry_10, R.id.entry_11, R.id.entry_12, R.id.entry_13, R.id.entry_14, R.id.entry_15, R.id.entry_16})
    public void onViewClicked(View view) {
        for (Integer id : classMap.keySet()) {
            if (view.getId() == id) {
                startActivity(new Intent(this, classMap.get(id)));
            }
        }
    }
}
