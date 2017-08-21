package com.example.myapplication.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: Administrator
 * @description: 请求拦截器(设置请求头以及一些公共参数)
 * @date: 2017-08-04   09:38
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = original
                .newBuilder()
                ///由于所有网络请求都是post json的方式,因此此处添加了公共头
                ///用户可以根据自己的业务自行修改
                .addHeader("Content-Type: application/json", "Accept: application/json");
        return chain.proceed(requestBuilder.build());
    }

}
