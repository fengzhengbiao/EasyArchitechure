package com.example.myapplication.presenter;

import android.util.Log;

import com.example.myapplication.contacts.MainActivityContact;
import com.example.myapplication.model.User;
import com.example.myapplication.subscribe.ProgressSubscrible;

/**
 * Created by JokerFish on 2017/8/17.
 */

public class MainpresenterImpl extends BasePresenter implements MainActivityContact.Presenter {

    private final MainActivityContact.View mainview;

    public MainpresenterImpl(MainActivityContact.View mainview) {
        this.mainview = mainview;
    }

    @Override
    public void calculate(int num1, int num2) {
        User user = new User("123456765432", "123456");
        requestUtil.login(user, new ProgressSubscrible<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess: "+s);
                mainview.signInSuccess(s);
            }
        });

    }
}
