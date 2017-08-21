package com.example.myapplication.subscribe;

import android.support.annotation.CallSuper;

import com.example.myapplication.MyApplication;
import com.example.myapplication.utils.ToastUtis;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by su on 2017/5/26.
 */

public abstract class ProgressSubscrible<T> extends Subscriber<T> {

    private ProgressDialogHandler handler;
//    private Context context;

    public ProgressSubscrible() {
        this.handler = new ProgressDialogHandler(false);
    }

    @CallSuper
    @Override
    public void onStart() {
        showProgressDialog();
    }

    @CallSuper
    @Override
    public void onCompleted() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
        dissmissDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            ToastUtis.showToast(MyApplication.getInstance(), "网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtis.showToast(MyApplication.getInstance(), "网络中断，请检查您的网络状态");
        } else {
            ToastUtis.showToast(MyApplication.getInstance(), "网络请求出错");
        }
        onCompleted();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onCompleted();
    }

    private void showProgressDialog() {
        if (handler != null) {
            handler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dissmissDialog() {
        if (handler != null) {
            handler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
        handler = null;
    }

    public abstract void onSuccess(T t);

}
