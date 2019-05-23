package com.cd.mytestdemo.studentbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cd.mytestdemo.R;
import com.cd.mytestdemo.utils.InjectUtils;
import com.cd.mytestdemo.utils.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class StudentBoxActivity extends AppCompatActivity {
    @ViewInject(id = R.id.rclv_student_discuss)
    RecyclerView rclvStudentDiscuss;

    private StudentDiscussAdapter studentDiscussAdapter;
    private StudentDiscussAdapter1 studentDiscussAdapter1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_box);
        InjectUtils.inject(this);

        initAdapter();
    }

    private void initAdapter() {
        studentDiscussAdapter = new StudentDiscussAdapter(this, getList(), R.layout.student_discuss_item);
        studentDiscussAdapter1 = new StudentDiscussAdapter1(getList());
        rclvStudentDiscuss.setLayoutManager(new LinearLayoutManager(this));
        rclvStudentDiscuss.setAdapter(studentDiscussAdapter1);
    }

    private List<String> getList() {
        List mList = new ArrayList();
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
        return mList;
    }
}
