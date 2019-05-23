package com.cd.mytestdemo.factory;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 11:29
 * <p>
 * Describe:
 */
public class CarEventView {
    private BaseObject carObj;

    public CarEventView() {

    }

    public void setCarFactory(BaseObject carObj) {
        this.carObj = carObj;

        carObj.drive();
        carObj.getName();
    }
}
