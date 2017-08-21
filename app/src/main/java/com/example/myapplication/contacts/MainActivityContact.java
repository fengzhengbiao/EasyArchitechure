package com.example.myapplication.contacts;

import com.example.myapplication.ui.IBaseView;

/**
 * Created by JokerFish on 2017/8/18.
 */

public interface MainActivityContact {

    interface Presenter {

        void calculate(int num1, int num2);

    }

    interface View extends IBaseView {
        void signInSuccess(String knock);
    }
}
