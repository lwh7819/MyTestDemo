package com.cd.mytestdemo.factory;

import android.util.Log;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 10:58
 * <p>
 * Describe:
 */
public class BenzCar implements BaseObject {

    @Override
    public void drive() {
        Log.e("lwh", "drive Benz");
    }

    @Override
    public String getName() {
        return "Benz";
    }
}
