package com.cd.mytestdemo.hook;

import android.view.View;

public class RealClickFactory {
    public static final int FAST_DOUBLE_CLICK_ENTRY_TYPE = 1;
    public static final int FAST_DOUBLE_CLICK_IGNORE_TYPE = 2;
    public static final int OTHER_CLICK_IGNORE_TYPE = 3;

    public static CustomClick getRealClick(int type, View.OnClickListener onClickListener) {
        CustomClick realClick = null;
        switch (type) {
            case FAST_DOUBLE_CLICK_ENTRY_TYPE:
                realClick = new RealClick(onClickListener);
                break;
            case FAST_DOUBLE_CLICK_IGNORE_TYPE:
                realClick = new RealClick2(onClickListener);
                break;
            case OTHER_CLICK_IGNORE_TYPE:
                realClick = new RealClick(onClickListener);
                break;
        }
        return realClick;
    }
}
