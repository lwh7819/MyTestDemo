package com.cd.mytestdemo.studentbox;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewSparseArray;

    public BaseRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(@IdRes int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView文本
     */
    public BaseRecyclerHolder setText(@IdRes int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        if (tv != null)
            tv.setText(text);
        return this;
    }

    /**
     * 设置View的Visibility
     */
    public BaseRecyclerHolder setViewVisibility(@IdRes int viewId, int visibility) {
        View mView = getView(viewId);
        if (mView != null)
            mView.setVisibility(visibility);
        return this;
    }

    /**
     * 设置ImageView的资源
     */
    public BaseRecyclerHolder setImageResource(@IdRes int viewId, @DrawableRes int resourceId) {
        ImageView imageView = getView(viewId);
        if (imageView != null)
            imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置View的背景
     */
    public BaseRecyclerHolder setViewBackground(@IdRes int viewId, @DrawableRes int resourceId) {
        View mView = getView(viewId);
        if (mView != null)
            mView.setBackgroundResource(resourceId);
        return this;
    }

    /**
     * 设置条目点击事件
     */
    public void setOnItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    /**
     * 设置条目长按事件
     */
    public void setOnItemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

}