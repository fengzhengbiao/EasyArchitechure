package com.example.myapplication.network;

import android.util.Log;

import com.example.myapplication.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author: Administrator
 * @description:
 * @date: 2017-08-04   09:21
 */
public class OkhttpFactory {

    private static OkHttpClient mOkHttpClient;

    //设置缓存目录
    private static final File cacheDirectory = new File(MyApplication.getInstance().getCacheDir().getAbsolutePath(), "httpCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    //请求拦截
    private static Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            //请求定制：添加请求头
            Request.Builder requestBuilder = original
                    .newBuilder()
                    ///由于所有网络请求都是post json的方式,因此此处添加了公共头
                    ///用户可以根据自己的业务自行修改
                    .addHeader("header1", "headerparams1");
            return chain.proceed(requestBuilder.build());
        }

    };

    //响应拦截器
    private static Interceptor responseInterceptor = new Interceptor() {
        String emptyString = ":\"\"";
        String emptyObject = ":{}";
        String emptyArray = ":[]";
        String newChars = ":null";

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Response response = chain.proceed(request);
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String json = responseBody.string();
                MediaType contentType = responseBody.contentType();
                if (!json.contains(emptyString)) {
                    ResponseBody body = ResponseBody.create(contentType, json);
                    return response.newBuilder().body(body).build();
                } else {
                    String replace = json.replace(emptyString, newChars);
                    String replace1 = replace.replace(emptyObject, newChars);
                    String replace2 = replace1.replace(emptyArray, newChars);
                    ResponseBody body = ResponseBody.create(contentType, replace2);
                    return response.newBuilder().body(body).build();
                }
            }
            return response;
        }
    };

    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .cookieJar(CookieJar.NO_COOKIES)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(responseInterceptor)
                    .addInterceptor(new HttpLoggingInterceptor(message -> Log.i("http", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cache(cache)
                    .build();
        }
        return mOkHttpClient;
    }
}
