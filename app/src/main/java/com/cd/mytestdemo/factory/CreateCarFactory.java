package com.cd.mytestdemo.factory;

/**
 * Created by LvWeiHao
 * Date: 2019/4/16 0016 11:00
 * <p>
 * Describe:
 */
public class CreateCarFactory {

    public static CreateCarFactory newInstance() {
        return new CreateCarFactory();
    }

    public BaseObject createCar(int tag) {
        BaseObject carObject = null;
        switch (tag) {
            case 0:
                carObject = new BenzCar();
                break;
            case 1:
                carObject = new BmwCar();
                break;
        }
        return carObject;
    }
}
