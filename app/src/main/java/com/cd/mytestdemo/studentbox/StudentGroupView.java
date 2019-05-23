package com.cd.mytestdemo.studentbox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.mytestdemo.R;
import com.cd.mytestdemo.utils.InjectUtils;
import com.cd.mytestdemo.utils.ViewInject;

public class StudentGroupView extends LinearLayout {
    @ViewInject(id = R.id.tv_group_name)
    TextView tvGroupName;
    @ViewInject(id = R.id.tv_group_size)
    TextView tvGroupSize;
    @ViewInject(id = R.id.ll_header_view)
    LinearLayout llHeaderView;
    @ViewInject(id = R.id.rclv_student_discuss_item)
    RecyclerView rclvStudentDiscussItem;
    private Context mContext;

    public StudentGroupView(Context context) {
        this(context, null);
    }

    public StudentGroupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StudentGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.student_discuss_item, this);
        InjectUtils.inject(view, this);
        rclvStudentDiscussItem.setEnabled(false);
        rclvStudentDiscussItem.setLayoutManager(new GridLayoutManager(mContext,9));
        rclvStudentDiscussItem.setAdapter(new StudentDiscussItemAdapter());
    }

    public void setTvGroupName(String groupName) {
        tvGroupName.setText(groupName);
    }

    public void setHeaderBgColor(int color) {
        llHeaderView.setBackgroundColor(mContext.getResources().getColor(color));
    }

    public void setTvGroupSize(int resourcesId, String sizeStr) {
        tvGroupSize.setText(String.format(mContext.getResources().getString(resourcesId), sizeStr));
    }
}
