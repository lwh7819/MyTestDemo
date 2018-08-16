package com.cd.mytestdemo.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cd.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv.weihao on 2018/8/6.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> imgs;
    private List<Integer> mHeights = new ArrayList<>();
    UpdateImg updateImg;
    private ImageUtils myImageLoade;

    ImageAdapter(List<String> imgs, Context context) {
        this.imgs = imgs;
        for (int i = 0; i < imgs.size(); i++) {
            mHeights.add((int) (200 + Math.random() * 300));
        }

        myImageLoade = new ImageUtils(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        myImageLoade.loadImage(imgs.get(position), holder.img);

        ViewGroup.LayoutParams params = holder.img.getLayoutParams();
        params.height = mHeights.get(position);
        holder.img.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
