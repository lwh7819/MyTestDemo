package com.cd.mytestdemo.tbs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cd.mytestdemo.MainActivity;
import com.cd.mytestdemo.R;
import com.cd.testlibirary.net.HttpClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.tencent.smtt.sdk.TbsReaderView;
import okhttp3.Response;

import java.io.File;

/**
 * Created by lv.weihao on 2018/6/7.
 */
public class TbsActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {
    @BindView(R.id.m_tbs_webView)
    X5WebView mTbsWebView;
    @BindView(R.id.tbs_view)
    RelativeLayout tbsView;
    private TbsReaderView mTbsReaderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        ButterKnife.bind(this);

//        mTbsWebView.loadUrl("file:///android_asset/webpage/fileChooser.html");
//        mTbsWebView.loadUrl("file:///android_asset/webpage/testPdf.pdf");
//        mTbsWebView.loadUrl("file:///android_asset/webpage/testDocx.docx");

        mTbsReaderView = new TbsReaderView(this, this);
        tbsView.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));

//        initDoc("https://github.com/lwh7819/source/raw/master/image/Android%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.docx", createFile());
//        initDoc("https://github.com/lwh7819/source/raw/master/image/tbsPdf.pdf", createFile());
        initDoc("https://github.com/lwh7819/source/raw/master/image/%E6%9D%AD%E5%B7%9E%E5%8C%BB%E4%B9%8B%E4%BF%A1-%E9%A1%B9%E7%9B%AE%E4%BB%8B%E7%BB%8D.pptx", createFile());
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initDoc(String docUrl, final String download) {
        int i = docUrl.lastIndexOf("/");
        String docName = docUrl.substring(i, docUrl.length());
        Log.d("print", "---substring---" + docName);

        //判断是否在本地/[下载/直接打开]
        File docFile = new File(download, docName);
        if (docFile.exists()) {
            //存在本地;
            Log.d("print", "本地存在");
            displayFile(docFile.toString(), docName);
        } else {
            //本地不存在,则下载;使用的OkGo2.x;
            OkGo.get(docUrl)
                    .tag(this)
                    .execute(new FileCallback(download, docName) {  //文件下载时，可以指定下载的文件目录和文件名
                        @Override
                        public void onSuccess(File file, okhttp3.Call call, Response response) {
                            // file 即为文件数据，文件保存在指定目录
                            Log.d("print", "下载文件成功");
                            displayFile(download + file.getName(), file.getName());
                            Log.d("print", "" + file.getName());
                        }

                        @Override
                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            //这里回调下载进度(该回调在主线程,可以直接更新ui)
                            Log.d("print", "总大小---" + totalSize + "---文件下载进度---" + progress);
                        }
                    });
        }
    }

    private void displayFile(String filePath, String fileName) {

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = createFile() + "/TbsReaderTemp";
        File bsReaderTempFile = new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d("print", "准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                Log.d("print", "创建/TbsReaderTemp失败！！！！！");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", bsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType(fileName), false);
        Log.d("print", "查看文档---" + result);
        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {

        }
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

    private String createFile() {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/aTbs");
        if (!file.exists()) {
            Log.d("print", "准备创建/file！！");
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                Log.d("print", "创建/file失败！！！！！");
            }
        }
        return file.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();//不关掉此界面，之后加载文件会无法加载
    }


}
