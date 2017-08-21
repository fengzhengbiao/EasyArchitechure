package com.example.myapplication.network;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.model.HttpResult;
import com.example.myapplication.model.User;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by JokerFish on 2017/8/18.
 */

public class RequestUtil {

    RequestService service;

    public RequestUtil(RequestService service) {
        this.service = service;
    }

    public void login(User user, Subscriber<String> subscriber) {
        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json"),
                        JSON.toJSONString(user));
        Observable observable = service.login(requestBody)
                .map(new HttpResultFunc());
        toSubscrible(observable, subscriber);
    }


    private <T> void toSubscrible(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            if (!tHttpResult.isSuccess()) {
                throw new ApiException(tHttpResult.getMessage());
            }
            return tHttpResult.getData();
        }
    }
}
