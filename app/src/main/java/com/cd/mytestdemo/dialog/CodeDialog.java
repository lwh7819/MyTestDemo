package com.cd.mytestdemo.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.util.PopUtil;

/**
 * Created by lv.weihao on 2018/5/21.
 */
public class CodeDialog implements BaseDialog.OnItemClickListener{
    private BaseDialog baseDialog;
    private Context mContext;

    private int[] listenerIDs;

    private EditText mEdt;
    private ImageView mImg;

    public CodeDialog(Context context) {
        this.mContext = context;
        listenerIDs = new int[] {R.id.edt, R.id.btn, R.id.img};
        baseDialog = new BaseDialog(context, R.layout.code_dialog, listenerIDs);
        baseDialog.setOnItemClickListener(this);
        mEdt = baseDialog.getContentView().findViewById(R.id.edt);
        mImg = baseDialog.getContentView().findViewById(R.id.img);

        mImg.setBackground(mContext.getResources().getDrawable(R.mipmap.ic_category));
    }

    public void show() {
        baseDialog.show();
    }

    @Override
    public void onItemClick(BaseDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.edt:
                PopUtil.showToast("edt");
                break;
            case R.id.btn:
                baseDialog.setDismiss();
                PopUtil.showToast(mEdt.getText().toString() + mImg.getTag().toString());
                break;
            case R.id.img:
                mImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_persion));
                mImg.setTag("12345");
                PopUtil.showToast("img");
                break;
        }
    }
}
