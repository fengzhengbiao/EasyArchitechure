package com.example.myapplication;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by JokerFish on 2017/8/17.
 */

public class MyApplication extends Application {
    private static MyApplication context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initLogger();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private void initLogger() {
        Logger.init("Test")             // default PRETTYLOGGER or use just init()
                .methodCount(2)         // default 2
                .hideThreadInfo()        // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)     // default LogLevel.FULL
                .methodOffset(2);             // default 0

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static MyApplication getInstance() {
        return context;
    }
}
