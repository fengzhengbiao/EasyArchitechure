package com.example.myapplication;

import com.example.myapplication.presenter.BasePresenter;
import com.example.myapplication.ui.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by JokerFish on 2017/8/17.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(BaseActivity baseActivity);

    void inject(BasePresenter basePresenter);
}
