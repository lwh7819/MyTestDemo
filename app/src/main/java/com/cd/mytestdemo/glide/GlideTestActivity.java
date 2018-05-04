package com.cd.mytestdemo.glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cd.mytestdemo.R;

/**
 * Created by lv.weihao on 2018/5/2.
 */
public class GlideTestActivity extends AppCompatActivity {
    @BindView(R.id.m_image_view)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);
        ButterKnife.bind(this);

        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String urlGif = "http://p1.pstatp.com/large/166200019850062839d3";
        Glide
                .with(this)
                .load(url)
                .transform(new CircleCrop(this))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_category)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(mImageView);

    }

    private SimpleTarget<GlideDrawable> createSimpleTarget() {
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mImageView.setImageDrawable(resource);
            }
        };
        return simpleTarget;
    }
}
