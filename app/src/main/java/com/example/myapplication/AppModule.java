package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.network.OkhttpFactory;
import com.example.myapplication.network.RequestService;
import com.example.myapplication.network.RequestUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JokerFish on 2017/8/17.
 */
@Module
public class AppModule {
    private static final String URL = "http://mx.liujingongchang.com/jackey/";

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkhttpFactory.getOkHttpClient())
                .build();
    }

    @Singleton
    @Provides
    RequestService provideRequestApi(Retrofit retrofit) {
        return retrofit.create(RequestService.class);
    }

    @Singleton
    @Provides
    RequestUtil provideRequestUtil(RequestService requestService) {
        return new RequestUtil(requestService);
    }


}
