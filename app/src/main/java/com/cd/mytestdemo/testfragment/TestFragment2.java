package com.cd.mytestdemo.testfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.base.BaseActivity;

/**
 * Created by lv.weihao on 2018/5/11.
 */
public class TestFragment2 extends Fragment {
    @BindView(R.id.m_tv_text)
    TextView mTvText;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ont, container, false);

        unbinder = ButterKnife.bind(this, v);

        mTvText.setText("two");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseActivity activity = (BaseActivity) getActivity();
        activity.setBackButtonVisible(true);
        activity.setTitle("fragment2");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.m_tv_text)
    public void onViewClicked() {
        TestFragment3 testFragment3 = new TestFragment3();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.push_left_enter, R.anim.push_left_exit, R.anim.push_right_enter, R.anim.push_right_exit);
        ft.replace(R.id.content_view, testFragment3);
        ft.addToBackStack(null);
        ft.commit();
    }
}
