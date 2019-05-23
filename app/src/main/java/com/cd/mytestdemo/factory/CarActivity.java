package com.cd.mytestdemo.factory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 11:05
 * <p>
 * Describe:
 */
public class CarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BenzCar benzCar = (BenzCar) CreateCarFactory.newInstance().createCar(0);
        CarEventView carEventView = new CarEventView();
        carEventView.setCarFactory(benzCar);
    }
}
