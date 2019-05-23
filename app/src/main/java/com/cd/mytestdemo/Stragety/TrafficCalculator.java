package com.cd.mytestdemo.Stragety;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 11:52
 * <p>
 * Describe:
 */
public class TrafficCalculator {
    private CalculateStragety stragety;

    public void setCalculator(CalculateStragety stragety) {
        this.stragety = stragety;
    }

    public int getPrice(int km) {
        return stragety.calculatePrice(km);
    }
}
