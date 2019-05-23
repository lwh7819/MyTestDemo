package com.cd.mytestdemo.factory;

import android.util.Log;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 10:57
 * <p>
 * Describe:
 */
public class BmwCar implements BaseObject {

    @Override
    public void drive() {
        Log.e("lwh", "drive Bmw");
    }

    @Override
    public String getName() {
        return "Bmw";
    }
}
