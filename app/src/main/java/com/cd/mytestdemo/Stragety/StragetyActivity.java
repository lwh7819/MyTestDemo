package com.cd.mytestdemo.Stragety;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 11:56
 * <p>
 * Describe:
 */
public class StragetyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TrafficCalculator calculator = new TrafficCalculator();
        calculator.setCalculator(new BusStragety());
        calculator.setCalculator(new TexiStragety());
        calculator.getPrice(2);
    }
}
