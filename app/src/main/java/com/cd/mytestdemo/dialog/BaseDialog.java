package com.cd.mytestdemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import com.cd.mytestdemo.R;

/**
 * Created by lv.weihao on 2018/5/21.
 */
public class BaseDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private int[] listernerIDs;

    private OnItemClickListener listener;
    private View mContentView;

    public BaseDialog(Context context, int resID, int[] listenerIDs) {
        super(context, R.style.Code_Dialog);
        this.mContext = context;
        this.listernerIDs = listenerIDs;
        mContentView = LayoutInflater.from(mContext).inflate(resID, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setWindowAnimations(R.style.bottom_menu_animation);
        setContentView(mContentView);

        WindowManager wm = ((Activity)mContext).getWindowManager();
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
//        lp.width = display.getWidth() * 4/5;
//        window.setAttributes(lp);
        setCancelable(true);

        for (int id : listernerIDs) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(this, v);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseDialog dialog, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDismiss() {
        dismiss();
    }

    public View getContentView() {
        return mContentView;
    }
}
