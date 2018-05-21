package com.cd.mytestdemo.common.view.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by lv.weihao on 2018/5/18.
 */
public class MyApp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
