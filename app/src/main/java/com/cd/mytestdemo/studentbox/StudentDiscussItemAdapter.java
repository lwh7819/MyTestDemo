package com.cd.mytestdemo.studentbox;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class StudentDiscussItemAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List mList;

    public StudentDiscussItemAdapter() {
        mList = new ArrayList();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new StudentBoxView(parent.getContext());
        return new ViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StudentBoxView studentBoxView = (StudentBoxView) holder.itemView.getRootView();
        studentBoxView.setStudentSelected(studentBoxView.getStudentSelected());
        studentBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentBoxView.setStudentSelected(!studentBoxView.getStudentSelected());
            }
        });
        if (position == 0) {
            studentBoxView.setIsLeader(true);
        }
        if (position % 2 == 0) {
            studentBoxView.setLevelType(1);
        } else {
            studentBoxView.setLevelType(2);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
