package com.cd.mytestdemo.animate;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.cd.mytestdemo.R;

public class AnimateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        final ImageView imageView = (ImageView) findViewById(R.id.iv_animate);
        final ImageView imageView1 = (ImageView) findViewById(R.id.iv_animate1);
        final ImageView imageView2 = (ImageView) findViewById(R.id.iv_animate2);
        imageView.setBackgroundResource(R.mipmap.audio_icon_1);
        imageView1.setBackgroundResource(R.drawable.intreval_animate);
        imageView2.setBackgroundResource(R.drawable.intreval_animate);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getBackground() instanceof AnimationDrawable) {
                    if (((AnimationDrawable) imageView.getBackground()).isRunning()) {
                        ((AnimationDrawable) imageView.getBackground()).stop();
                    } else {
                        ((AnimationDrawable) imageView.getBackground()).start();
                    }
                }
                if (imageView1.getBackground() instanceof AnimationDrawable) {
                    if (((AnimationDrawable) imageView1.getBackground()).isRunning()) {
                        ((AnimationDrawable) imageView1.getBackground()).stop();
                    } else {
                        ((AnimationDrawable) imageView1.getBackground()).start();
                    }
                }
                if (imageView2.getBackground() instanceof AnimationDrawable) {
                    if (((AnimationDrawable) imageView2.getBackground()).isRunning()) {
                        ((AnimationDrawable) imageView2.getBackground()).stop();
                    } else {
                        ((AnimationDrawable) imageView2.getBackground()).start();
                    }
                }
            }
        });
    }
}
