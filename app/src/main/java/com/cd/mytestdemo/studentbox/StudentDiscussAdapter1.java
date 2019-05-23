package com.cd.mytestdemo.studentbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class StudentDiscussAdapter1 extends RecyclerView.Adapter<ViewHolder> {
    private List mList;
    private Context mContext;

    public StudentDiscussAdapter1(List list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = new StudentGroupView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        RecyclerView rclvStudentDiscussItem = holder.itemView.getRootView().findViewById(R.id.rclv_student_discuss_item);
//        rclvStudentDiscussItem.setEnabled(false);
//        rclvStudentDiscussItem.setLayoutManager(new GridLayoutManager(mContext,9));
//        rclvStudentDiscussItem.setAdapter(new StudentDiscussItemAdapter());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
