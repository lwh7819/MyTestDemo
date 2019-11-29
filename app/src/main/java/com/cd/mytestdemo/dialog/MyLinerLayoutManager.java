package com.cd.mytestdemo.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class MyLinerLayoutManager extends LinearLayoutManager {
    private boolean canScrollVertical = true;
    public MyLinerLayoutManager(Context context) {
        super(context);
    }

    public MyLinerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return canScrollVertical && super.canScrollVertically();
    }

    public void setCanScrollVertical(boolean canScrollVertical) {
        this.canScrollVertical = canScrollVertical;
    }
}
