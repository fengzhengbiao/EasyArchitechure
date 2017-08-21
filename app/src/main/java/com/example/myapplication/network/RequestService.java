package com.example.myapplication.network;

import com.example.myapplication.model.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author: Administrator
 * @description:    Retrofit 中的请求方法
 * @date: 2017-08-04   13:52
 */
public interface RequestService {

    @POST("user/login")
    Observable<HttpResult<String>> login(@Body RequestBody requestBody);

}
