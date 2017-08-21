package com.example.myapplication.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by JokerFish on 2017/07/11.
 * 吐司工具类
 */

public class ToastUtis {
    private static Toast sToast;

    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showToast(Context context, @StringRes int resId) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), context.getResources().getString(resId), Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setText(context.getResources().getString(resId));
        }
        sToast.show();
    }

    public static void cancleToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
