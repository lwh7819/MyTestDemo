package com.cd.mytestdemo.hook;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookViewClickUtil {

    /**
     * 通过注解HookClick拦截点击事件
     * @param target
     */
    public static void hookViews(Object target) {
            Class<?> clazz = target.getClass();
            Field[] fields = clazz.getDeclaredFields();
            HookClick hookClick;
            int id;
            int type;
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof HookClick) {
                        hookClick = field.getAnnotation(HookClick.class);
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        id = hookClick.value();
                        type = hookClick.type();
                        if (target instanceof Activity) {
                            View view = ((Activity)target).findViewById(id);
                            hookView(view, type);
                        } else if (target instanceof View) {
                            View view = ((View)target).findViewById(id);
                            hookView(view, type);
                        }
                    }
                }
            }
    }

    public static void hookView(View view, int type) {
        try {
            Class viewClazz = Class.forName("android.view.View");
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
            if (!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerInfo = listenerInfoMethod.invoke(view);

            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");
            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");
            if (!onClickListenerField.isAccessible()) {
                onClickListenerField.setAccessible(true);
            }
            View.OnClickListener mOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfo);
            View.OnClickListener mProxyListener = new OnClickListenerProxy(mOnClickListener, type);
            onClickListenerField.set(listenerInfo, mProxyListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义的代理事件监听器
    private static class OnClickListenerProxy implements View.OnClickListener {

        private final CustomClick realClick;
        private final ProxyClick proxyClick;
        private final CustomClick customClick;
        private View.OnClickListener object;

        private int MIN_CLICK_DELAY_TIME = 1000;

        private long lastClickTime = 0;

        private OnClickListenerProxy(View.OnClickListener object, int type) {
            this.object = object;
            if (type == 1) {
                realClick = new RealClick(this.object);
            } else {
                realClick = new RealClick2(this.object);
            }
            proxyClick = new ProxyClick(realClick);
            customClick = (CustomClick) Proxy.newProxyInstance(CustomClick.class.getClassLoader(), realClick.getClass().getInterfaces(), proxyClick);
        }

        @Override
        public void onClick(View v) {

            customClick.click(v);

//            Log.e("OnClickListenerProxy", "OnClickListenerProxy" + this.object.toString());
//            long currentTime = Calendar.getInstance().getTimeInMillis();
//            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
//                lastClickTime = currentTime;
////                if (object != null) object.onClick(v);
//            } else {
//                if (object != null) object.onClick(v);
//            }
        }
    }
}
