package com.cd.mytestdemo.viewpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cd.mytestdemo.R;
import com.lvweihao.slideshow.MyCirclePageIndicator;

import io.reactivex.disposables.CompositeDisposable;

import java.util.ArrayList;

/**
 * Created by lv.weihao on 2018/5/22.
 */
public class ViewPageActivity extends AppCompatActivity {
    @BindView(R.id.m_view_page)
    ViewPager mViewPage;
    @BindView(R.id.cp_circle)
    MyCirclePageIndicator cpCircle;

    private ArrayList<String> urls = new ArrayList<>();
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        ButterKnife.bind(this);

        urls.add("http://seopic.699pic.com/photo/00005/5186.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50010/0719.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50009/9449.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50002/5923.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50001/9330.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50009/9191.jpg_wh1200.jpg");

        CarouselAdapter carouselAdapter = new CarouselAdapter(this, urls, mViewPage);
        compositeDisposable = new CompositeDisposable();
//        compositeDisposable.add(carouselAdapter.startInterval());
        carouselAdapter.startInterval(5000);
        cpCircle.setViewPager(mViewPage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
