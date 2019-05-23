package com.cd.mytestdemo.studentbox;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    private LayoutInflater layoutInflater;

    private List<T> mData;

    private int layoutId;

    // 多布局支持
    private MultiTypeSupport<T> mMultiTypeSupport;

    public BaseRecycleAdapter(@NonNull Context context, @NonNull List<T> mData, @LayoutRes int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.layoutId = layoutId;
    }

    public BaseRecycleAdapter(@NonNull Context context, @NonNull List<T> mData, MultiTypeSupport<T> mMultiTypeSupport) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.mMultiTypeSupport = mMultiTypeSupport;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            layoutId = viewType;
        }
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new BaseRecyclerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        bindData(holder, mData.get(position), position);
    }

    public abstract void bindData(@NonNull BaseRecyclerHolder holder, @NonNull T data, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 多布局支持
        if (mMultiTypeSupport != null)
            return mMultiTypeSupport.getLayoutId(mData.get(position), position);
        return super.getItemViewType(position);
    }

    public void setDataList(@NonNull Collection<T> list) {
        if (mData == null)
            return;
        this.mData.clear();
        this.mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(@NonNull Collection<T> list) {
        if (mData == null)
            return;
        int lastIndex = this.mData.size();
        if (this.mData.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void addData(@NonNull T data) {
        if (mData == null)
            return;
        int lastIndex = this.mData.size();
        if (this.mData.add(data)) {
            notifyItemInserted(lastIndex);
        }
    }
}