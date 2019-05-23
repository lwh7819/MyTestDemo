package com.cd.mytestdemo.studentbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cd.mytestdemo.R;

import java.util.List;


public class StudentDiscussAdapter extends BaseRecycleAdapter<String> {

    private Context mContext;
    private List mList;

    public StudentDiscussAdapter(@NonNull Context context, @NonNull List<String> mData, int layoutId) {
        super(context, mData, layoutId);
        this.mContext = context;
        this.mList = mData;
    }


    @Override
    public void bindData(@NonNull BaseRecyclerHolder holder, @NonNull String data, int position) {
        RecyclerView rclvStudentDiscussItem = holder.getView(R.id.rclv_student_discuss_item);
        rclvStudentDiscussItem.setEnabled(false);
        rclvStudentDiscussItem.setLayoutManager(new GridLayoutManager(mContext,8));
        rclvStudentDiscussItem.setAdapter(new StudentDiscussItemAdapter());
    }

}
