package com.cd.mytestdemo.Stragety;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 11:49
 * <p>
 * Describe:
 */
public class BusStragety implements CalculateStragety {
    @Override
    public int calculatePrice(int km) {
        return km *2 / 5;
    }
}
