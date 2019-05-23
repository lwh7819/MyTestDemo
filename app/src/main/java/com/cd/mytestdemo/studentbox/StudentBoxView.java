package com.cd.mytestdemo.studentbox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.mytestdemo.R;
import com.cd.mytestdemo.utils.InjectUtils;
import com.cd.mytestdemo.utils.ViewInject;

public class StudentBoxView extends LinearLayout {
    @ViewInject(id = R.id.iv_student_leader)
    ImageView ivStudentLeader;
    @ViewInject(id = R.id.tv_student_level)
    TextView tvStudentLevel;
    @ViewInject(id = R.id.tv_student_name)
    TextView tvStudentName;

    private View view;
    private Context mContext;
    private boolean isLeader;
    private boolean isStudentSelected;

    public StudentBoxView(Context context) {
        this(context, null);
    }

    public StudentBoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StudentBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        view = inflate(mContext, R.layout.student_box_view, this);
        InjectUtils.inject(view, this);

    }

    /**
     * 设置是否选中
     * @param isSelected
     */
    public void setStudentSelected(boolean isSelected) {
        this.isStudentSelected = isSelected;
        this.setSelected(this.isStudentSelected);
        if (this.isStudentSelected) {
            tvStudentName.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            tvStudentName.setTextColor(mContext.getResources().getColor(R.color.text_color));
        }
    }

    public boolean getStudentSelected() {
        return isStudentSelected;
    }

    /**
     * 显示小组长图标
     */
    public void showStudentLeaderView(boolean isLeader) {
        if (isLeader) {
            ivStudentLeader.setVisibility(VISIBLE);
        } else {
            ivStudentLeader.setVisibility(GONE);
        }
    }

    /**
     * 设置学科能力等级
     * @param levelType
     */
    public void setLevelType(int levelType) {
        switch (levelType) {
            case 1:
                tvStudentLevel.setBackground(mContext.getResources().getDrawable(R.drawable.level_blue));
                break;
            case 2:
                tvStudentLevel.setBackground(mContext.getResources().getDrawable(R.drawable.level_green));
                break;
            case 3:
                tvStudentLevel.setBackground(mContext.getResources().getDrawable(R.drawable.level_red));
                break;
                default:

        }
    }

    /**
     * 设置是否为小组长
     * @param isLeader
     */
    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
        showStudentLeaderView(this.isLeader);
    }

    /**
     * 设置学生姓名
     * @param studentName
     */
    public void setStudentName(String studentName) {
        tvStudentName.setText(studentName);
    }
}
