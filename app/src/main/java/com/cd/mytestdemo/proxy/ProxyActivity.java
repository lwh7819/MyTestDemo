package com.cd.mytestdemo.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Proxy;

/**
 * Created by LvWeiHao
 * Date: 2019/4/17 0017 9:59
 * <p>
 * Describe:
 */
public class ProxyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Subject subject = new RealSubject();
        ProxySubject proxySubject = new ProxySubject(subject);
        Subject sub = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), subject.getClass().getInterfaces(), proxySubject);
        for (Class<?> cls : subject.getClass().getInterfaces()) {
            Log.e("lwh", "\n" + cls.getName());
        }
        sub.operation();
    }
}
