package com.cd.mytestdemo.common.view.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cd.mytestdemo.R;
import com.cd.mytestdemo.common.view.base.MyApp;

import java.util.Calendar;

/**
 * 弹出框工具类
 */
public class PopUtil {

    private static Toast sToast = null;
    private static Dialog sProgress = null;
    private static AlertDialog mAlertDialog = null;

    /**
     * 显示弹出
     *
     * @param
     * @param text    CharSequence对象
     */
    public static void showToast(CharSequence text) {
        if (sToast != null) {
            sToast.cancel();
        }

        sToast = Toast.makeText(MyApp.getAppContext(), text, Toast.LENGTH_LONG);
        sToast.show();
    }

    /**
     * 显示弹出
     *
     * @param
     * @param resId   资源ID
     */
    public static void showToast(int resId) {
        showToast(MyApp.getAppContext().getText(resId));
    }


    /**
     * 隐藏等待框
     */
    public static void dismissWaitingDialog() {
        if (sProgress != null) {
            sProgress.dismiss();
            sProgress = null;
        }
    }

    public static void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = null;
        timePickerDialog = new TimePickerDialog(context, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public static void hideAlertDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

}
