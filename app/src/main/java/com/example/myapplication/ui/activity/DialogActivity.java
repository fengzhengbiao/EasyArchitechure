package com.example.myapplication.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Window;

import com.example.myapplication.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by su on 2017/5/31.
 */

public class DialogActivity extends Activity {

    private ExitReceiver receiver;
    private LocalBroadcastManager manager;
    private boolean cancleable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Intent intent = getIntent();
        cancleable = getIntent().getBooleanExtra("cancleable", false);
        Window window = getWindow();
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.demo.dialog.dismiss");
        receiver = new ExitReceiver();
        manager.registerReceiver(receiver, filter);
    }

    @Override
    public void onBackPressed() {
        if (cancleable) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            manager.unregisterReceiver(receiver);
        }

        receiver = null;
        manager = null;
    }

    public static void show(Context context, boolean cancleable) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("cancleable", cancleable);
        context.startActivity(intent);
    }

    public static void dismiss(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.demo.dialog.dismiss");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);

    }

    private class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }
}
