package com.example.myapplication.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.AppComponent;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ui.IBaseView;
import com.example.myapplication.utils.ToastUtis;

import butterknife.ButterKnife;

/**
 * Created by JokerFish on 2017/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpComponent(MyApplication.getInstance().getAppComponent());
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
    }
    //使用mvp框架的activity需要重写该方法
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    public void showMessage(String msg) {
        ToastUtis.showToast(this, msg);
    }

    public abstract int getLayoutResource();
}
