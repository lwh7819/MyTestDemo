package com.cd.mytestdemo.cache;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.cd.mytestdemo.R;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lv.weihao on 2018/8/6.
 */

public class ImageUtils {
    private static final int TAG_KEY_URI = R.id.img;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 *50;
    private static final int DISK_CACHE_INDEX = 0;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);

    private Context context;

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private boolean mIsDiskLruCacheCreated = false;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                LoaderResult loaderResult = (LoaderResult) msg.obj;
                ImageView imageView = loaderResult.imageView;
                String url = (String) imageView.getTag(TAG_KEY_URI);
                if (url.equals(loaderResult.uri)) {
                    imageView.setImageBitmap(loaderResult.bitmap);
                } else {
                    Log.w("lwh", "set image bitmap, but has changed,ignored!");
                }
            }
        }
    };

    public ImageUtils(Context context) {
        Log.e("lwc", "cpu:" + CPU_COUNT + "    max:" + MAXIMUM_POOL_SIZE);
        this.context = context;
        int maxSize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxSize / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File diskCacheDir = getDiskCacheDir("bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getUseableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, getAppVersion(), 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadImage(final String urlStr, final ImageView imageView) {
        imageView.setTag(TAG_KEY_URI, urlStr);
        Bitmap bitmap = loadBitmapFromMemoryCache(urlStr);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(urlStr);
                LoaderResult loaderResult = new LoaderResult(imageView, urlStr, bitmap);
                handler.obtainMessage(1, loaderResult).sendToTarget();
            }
        };

        threadPoolExecutor.execute(runnable);
    }

    private Bitmap loadBitmap(String url) {
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null) {
            return bitmap;
        }
        try {
            bitmap = loadBitmapFromDiskCache(url);
            if (bitmap != null) {
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            bitmap = downLoadBitmapFromUrl(url);
        }
        return bitmap;
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    private Bitmap loadBitmapFromMemoryCache(String url) {
        String key = hashKeyFromUrl(url);
        Bitmap bitmap = getBitmapFromMemoryCache(key);
        return bitmap;
    }

    private Bitmap loadBitmapFromDiskCache(String url) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w("lwh", "load bitmap from UI Thread, it's not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = scaleImg(fileDescriptor, 100, 200);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        Log.e("lwc", Thread.currentThread().getName());
        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String url) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w("lwh", "load bitmap from UI Thread, it's not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downLoadBitmapFromUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url);
    }

    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private boolean downLoadBitmapFromUrlToStream(String urlStr, OutputStream outputStream) {
        HttpURLConnection httpURLConnection = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;

        try {
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            bos  = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Bitmap downLoadBitmapFromUrl(String urlStr) {
        HttpURLConnection httpURLConnection = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;

        try {
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            Bitmap bitmap = BitmapFactory.decodeStream(bis);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Bitmap scaleImg(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final  int halfHeight = height / 2;
            final  int halfWidth = width / 2;

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        return bitmap;
    }

    private File getDiskCacheDir(String uniqueName) {
        boolean externalStroageAvailabe = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String cachePath;
        if (externalStroageAvailabe) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUseableSpace(File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            return file.getUsableSpace();
        }
        StatFs statFs = new StatFs(file.getPath());
        return (long)statFs.getBlockSize() * (long)statFs.getAvailableBlocks();
    }

    /**
     * 获取当前应用程序的版本号。
     */
    private int getAppVersion() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    private class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView =imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }
}
