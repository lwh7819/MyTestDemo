package com.cd.mytestdemo.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by LvWeiHao
 * Date: 2019/4/17 0017 9:54
 * <p>
 * Describe:
 */
public class ProxyClick implements InvocationHandler {
    protected CustomClick customClick;

    public ProxyClick(CustomClick customClick) {
        this.customClick = customClick;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(customClick, args);
    }
}
