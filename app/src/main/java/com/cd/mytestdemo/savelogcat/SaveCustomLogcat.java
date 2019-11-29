package com.cd.mytestdemo.savelogcat;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 硬件信息及自定义的日志信息保存到sd卡
 */
public class SaveCustomLogcat {
    private static SaveCustomLogcat mInstance = null;
    private String mFilename;
    private String SD_PATH;
    private WriterAsyncTask writerAsyncTask;

    public static SaveCustomLogcat getInstance() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new SaveCustomLogcat();
            return mInstance;
        }
    }

    public void saveLog(String savePath, String logStr) {
        if (TextUtils.isEmpty(savePath)) {
            SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/saveLog";
        } else {
            SD_PATH = savePath;
        }
        File saveFolder = new File(SD_PATH);
        if (!saveFolder.exists() && !saveFolder.isDirectory()) {
            saveFolder.mkdirs();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        mFilename = df.format(new Date());// new Date()为获取当前系统时间
        mFilename = SD_PATH + "/" + mFilename + ".txt";
        File file = new File(mFilename);
        if (!file.exists()) {
            try {
                file.createNewFile(); // 创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mInstance.saveLogToFile("---------------------------------->\n" + logStr);
    }

    private void saveLogToFile(String logStr) {
        // 启动线程在文件末尾追加自定义log
        writerAsyncTask = new WriterAsyncTask() {
            @Override
            void onComplete() {
                if (writerAsyncTask != null) {
                    if (!writerAsyncTask.isCancelled()) {
                        writerAsyncTask.cancel(true);
                    }
                    writerAsyncTask = null;
                }
            }
        };
        writerAsyncTask.execute(mFilename, logStr);
    }

    public class LogWriter {
        public synchronized void writeToFile(String filename, String logStr) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File(filename));
                // 收集手机信息
                collectCrashDeviceInfo(pw);
                pw.write(logStr);
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 收集程序崩溃的设备信息
     */
    public void collectCrashDeviceInfo(PrintWriter pw) {
        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            final Method method2 = activityThreadClass.getMethod(
                    "currentActivityThread", new Class[0]);
            // 得到当前的ActivityThread对象
            Object localObject = method2.invoke(null, (Object[]) null);

            final Method method = activityThreadClass
                    .getMethod("getApplication");
            application = (Application) method.invoke(localObject, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            pw.println("time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date())); // 记录错误发生的时间
            if (application != null) {
                Context mAppContext = application.getApplicationContext();
                PackageManager pm = mAppContext.getPackageManager();
                PackageInfo mPackageInfo = null;
                mPackageInfo = pm.getPackageInfo(mAppContext.getPackageName(), PackageManager.GET_ACTIVITIES);
                pw.println("versionCode: " + mPackageInfo.versionCode); // 版本号
                pw.println("versionName: " + mPackageInfo.versionName); // 版本名称
            }
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                pw.print(field.getName() + " : ");
                pw.println(field.get(null).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写操作放在子线程执行
     */
    abstract class WriterAsyncTask extends AsyncTask<String, Integer, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            LogWriter logWriter = new LogWriter();
            logWriter.writeToFile(strings[0], strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            onComplete();
        }

        abstract void onComplete();
    }
}