package com.cd.mytestdemo.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/5/18.
 */
public class CommonRecycleAdapter extends RecyclerView.Adapter<CommonRecycleAdapter.ViewHolder> {
    private List<BluetoothDevice> mList;
    private OnItemClick listener;

    public CommonRecycleAdapter(List<BluetoothDevice> mList) {
        if (mList == null) {
            mList = new ArrayList();
        }
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bluetooth, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BluetoothDevice device = getData(position);
        holder.mTvName.setText(device.getName());
        holder.mTvCode.setText(device.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public BluetoothDevice getData(int postion) {
        return mList.get(postion);
    }

    public void add (BluetoothDevice device) {
        mList.add(device);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;
        TextView mTvCode;
        TextView mTvState;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvName = itemView.findViewById(R.id.m_tv_name);
            mTvCode = itemView.findViewById(R.id.m_tv_code);
            mTvState = itemView.findViewById(R.id.m_tv_state);
        }
    }

    public void setOnItemClick(OnItemClick listener) {
        this.listener = listener;
    }

    interface OnItemClick {
        void onItemClick(View v, int postion);
    }
}
