package com.example.myapplication.dagger.component;

import com.example.myapplication.AppComponent;
import com.example.myapplication.dagger.ActivityScope;
import com.example.myapplication.dagger.module.MainActivityModule;
import com.example.myapplication.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by JokerFish on 2017/8/18.
 */
@ActivityScope
@Component(modules = MainActivityModule.class, dependencies = AppComponent.class)
public interface MainactivityComponent {
    void inject(MainActivity activity);
}
