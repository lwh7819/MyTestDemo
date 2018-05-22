package com.cd.mytestdemo.viewpage;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class BasePagerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {

    //当前页面
    private int currentPosition = 0;

    protected Context mContext;
    protected ArrayList<View> views;
    protected ViewPager mViewPager;

    public BasePagerAdapter(Context context, ArrayList<T> datas, ViewPager viewPager) {
        mContext = context;
        views = new ArrayList<>();
        //如果数据大于一条
        if (datas.size() > 1) {
            //添加最后一页到第一页
            datas.add(0, datas.get(datas.size() - 1));
            //添加第一页(经过上行的添加已经是第二页了)到最后一页
            datas.add(datas.get(1));
        }

        for (T data : datas) {
            views.add(getItemView(data));
        }

        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    protected abstract View getItemView(T data);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return;
        //若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(views.size() - 2, false);
        } else if (currentPosition == views.size() - 1) {
            //若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1, false);
        }
    }

    /**
     * 选择性是否开启轮询
     *
     * @return
     */
    public Disposable startInterval() {
        Disposable dispose = Observable.interval(3000, 3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        int currentIndex = mViewPager.getCurrentItem();
                        if (currentIndex == views.size() - 1) {
                            mViewPager.setCurrentItem(views.size() - 2, false);
                        }
                        mViewPager.setCurrentItem(++currentIndex, true);
                    }
                });
        return dispose;
    }
}