package com.example.myapplication.presenter;

import com.example.myapplication.MyApplication;
import com.example.myapplication.network.RequestUtil;

import javax.inject.Inject;

/**
 * Created by JokerFish on 2017/8/17.
 */

public class BasePresenter {
    public static final String TAG = "Retrofit";
    @Inject
    RequestUtil requestUtil;

    public BasePresenter() {
        MyApplication.getInstance().getAppComponent().inject(this);
    }
}
