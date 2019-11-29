package com.cd.mytestdemo.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

public class DoubleViewDialog implements BaseDialog.OnItemClickListener {
    private BaseDialog baseDialog;
    private int[] listenerIDs;
    private MyRecyclerView listView1;
    private MyRecyclerView listView2;
    private View llView2;
    private Context mContext;
    private List<String> mList;
    private boolean isListFirstScroll;

    public DoubleViewDialog(Context context) {
        this.mContext = context;
        listenerIDs = new int[] {R.id.view_1, R.id.view_2};
        baseDialog = new BaseDialog(context, R.layout.dialog_double_view, listenerIDs);
        baseDialog.setOnItemClickListener(this);
        listView1 = baseDialog.getContentView().findViewById(R.id.listView_1);
        listView2 = baseDialog.getContentView().findViewById(R.id.listView_2);
        llView2 = baseDialog.getContentView().findViewById(R.id.ll_view_2);
        mList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mList.add(i + "");
        }
        initAdapter();
    }

    private void initAdapter() {
        final MyLinerLayoutManager linearLayoutManager1 = new MyLinerLayoutManager(mContext);
        final MyLinerLayoutManager linearLayoutManager2 = new MyLinerLayoutManager(mContext);
        final RecyclerView.OnScrollListener onScrollListener1 = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    listView2.setCanTouch(true);
                    isListFirstScroll = true;
                } else {
                    listView2.setCanTouch(false);
                    isListFirstScroll = false;
                }
                Log.e("lwh", "state: " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                listView2.scrollBy(dx, dy);
            }
        };
        RecyclerView.OnScrollListener onScrollListener2 = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.e("lwh", "state: " + newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    listView1.setCanTouch(true);
                    listView1.removeOnScrollListener(onScrollListener1);
                } else {
                    listView1.setCanTouch(false);
                    listView1.addOnScrollListener(onScrollListener1);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isListFirstScroll) {
                    listView1.scrollBy(dx, dy);
                }
            }
        };
        listView1.setLayoutManager(linearLayoutManager1);
        listView1.setAdapter(new MyAdapter());

        listView2.setLayoutManager(linearLayoutManager2);
        listView2.setAdapter(new MyAdapter());

        listView1.addOnScrollListener(onScrollListener1);

        listView2.addOnScrollListener(onScrollListener2);
    }

    public void show() {
        baseDialog.show();
    }

    public void dismiss() {
        baseDialog.dismiss();
    }

    @Override
    public void onItemClick(BaseDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.view_1:
                llView2.setVisibility(View.VISIBLE);
                break;
            case R.id.view_2:
                llView2.setVisibility(View.GONE);
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_double_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvItem.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
