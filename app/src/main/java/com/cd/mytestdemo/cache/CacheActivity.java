package com.cd.mytestdemo.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lv.weihao on 2018/8/3.
 */

public class CacheActivity extends BaseActivity {

    LruCache<String, Bitmap> mMemoryCache;
    @BindView(R.id.tv_size)
    TextView tvSize;
    int i = 0;
    @BindView(R.id.lv)
    ListView lv;

    List<Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);

        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.e("lwh max:", maxMemorySize + "");
        int cacheSize = maxMemorySize / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //重写此方法来衡量每张图片的大小，默认返回图片数量
                return value.getByteCount() / 1024;
            }
        };

        bitmaps = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.mipmap.timg2, options);
            options.inSampleSize = 2;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg2, options);
            bitmaps.add(bitmap);
        }
        lv.setAdapter(new MyAdapter(this));
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    @OnClick({R.id.btn_add, R.id.btn_show_size})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg2);
                ++i;
                addBitmapToMemoryCache(i + "", bitmap);
                Log.e("lwh", i + "");
                break;
            case R.id.btn_show_size:
                tvSize.setText(mMemoryCache.size() + "");
                break;
        }
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return bitmaps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_img, parent, false);
            ((ImageView)v.findViewById(R.id.img)).setImageBitmap(bitmaps.get(position));
            return v;
        }
    }
}
