package com.example.myapplication.dagger.module;

import com.example.myapplication.contacts.MainActivityContact;
import com.example.myapplication.dagger.ActivityScope;
import com.example.myapplication.presenter.MainpresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokerFish on 2017/8/17.
 */
@Module
public class MainActivityModule {
    private MainActivityContact.View mainView;

    public MainActivityModule(MainActivityContact.View mainView) {
        this.mainView = mainView;
    }

    @Provides
    @ActivityScope
    MainActivityContact.Presenter mainActivity() {
        return new MainpresenterImpl(mainView);
    }

}
