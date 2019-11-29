package com.cd.mytestdemo.textureview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cd.mytestdemo.R;

import java.io.File;

/**
 * @author lvweihao
 * @date 2019/11/29
 * @description
 **/
public class TextureViewActivity extends AppCompatActivity implements View.OnClickListener, TextureView.SurfaceTextureListener {
    TextureView textureView;
    ImageView imageView;
    Button btn;
    Surface surface;
    MediaPlayer mMediaPlayer;
    private String mFilePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrue_view);

        mFilePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/VID_20191129_142018.3gp";
        textureView = findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(this);
        imageView = findViewById(R.id.image_view);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surface = new Surface(surface);
        if (new File(mFilePath).exists()) {
            play(mFilePath);    //播放视频
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        surface = null;
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void play(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {//文件不存在
                Toast.makeText(this, "文件路径错误", Toast.LENGTH_SHORT).show();
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(file.getAbsolutePath());
            mMediaPlayer.setSurface(surface);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap capture(Activity activity) {
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        return bmp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                imageView.setImageBitmap(textureView.getBitmap());
                break;
        }
    }
}
