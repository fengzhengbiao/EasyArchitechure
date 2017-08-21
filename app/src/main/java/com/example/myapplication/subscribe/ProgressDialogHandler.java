package com.example.myapplication.subscribe;

import android.os.Handler;
import android.os.Message;

import com.example.myapplication.MyApplication;
import com.example.myapplication.ui.activity.DialogActivity;

/**
 * Created by su on 2017/5/26.
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;


    private boolean cancleable;


    public ProgressDialogHandler(boolean cancleable) {
        super();
        this.cancleable = cancleable;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                DialogActivity.show(MyApplication.getInstance(), cancleable);
                break;
            case DISMISS_PROGRESS_DIALOG:
                DialogActivity.dismiss(MyApplication.getInstance());
                break;
        }

        super.handleMessage(msg);
    }
}
