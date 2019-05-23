package com.cd.mytestdemo.hook;

import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by LvWeiHao
 * Date: 2019/4/17 0017 9:54
 * <p>
 * Describe: 防止快速点击
 */
public class RealClick2 implements CustomClick {
    private View.OnClickListener object;

    private int MIN_CLICK_DELAY_TIME = 1000;

    private long lastClickTime = 0;

    public RealClick2(View.OnClickListener object) {
        this.object = object;
    }
    @Override
    public void click(View v) {
        Log.e("lwhcc", "OnClickListenerProxy" + this.object.toString());
        long currentTime = Calendar.getInstance().getTimeInMillis();
        Log.e("lwhcc", "currentTime" + currentTime);
        long chazhi = currentTime - lastClickTime;
        Log.e("lwhcc", "chazhi" + chazhi);
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            if (object != null) object.onClick(v);
        }
    }
}
