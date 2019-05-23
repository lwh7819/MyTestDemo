package com.cd.mytestdemo.proxy;

/**
 * Created by LvWeiHao
 * Date: 2019/4/17 0017 9:54
 * <p>
 * Describe:
 */
public class RealSubject implements Subject {
    @Override
    public String operation() {
        return "This is realSubject";
    }
}
