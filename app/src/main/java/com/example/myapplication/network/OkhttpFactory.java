package com.example.myapplication.network;

import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.MyApplication;
import com.example.myapplication.utils.SpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private static final File CACHEDIRECTORY = new File(MyApplication.getInstance().getCacheDir().getAbsolutePath(), "httpCache");
    private static final Cache CACHE = new Cache(CACHEDIRECTORY, 10 * 1024 * 1024);

    //请求拦截
    private static final Interceptor REQUEST_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            //请求定制：添加请求头
            Request.Builder requestBuilder = original
                    .newBuilder()
                    ///由于所有网络请求都是post json的方式,因此此处添加了公共头
                    ///用户可以根据自己的业务自行修改
                    .addHeader("header1", "headerparams1");
            if(original.body() instanceof FormBody){
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oidFormBody = (FormBody) original.body();
                for (int i = 0;i<oidFormBody.size();i++){
                    newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                }
                //统一添加请求体
                newFormBody.add("param1","value1");
                requestBuilder.method(original.method(),newFormBody.build());
            }

            return chain.proceed(requestBuilder.build());
        }

    };

    //响应拦截器
    private static final Interceptor RESPONSE_INTERCEPTOR = new Interceptor() {
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
                try {
                    //此处可以对网络请求进行统一解密
                } catch (Exception e) {
                    json = "";
                    Log.i("OkhttpFactory", "intercept: 解密失败");
                }

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


    //token拦截器(可用于Token失效自动重新请求)
    public static final Interceptor TOKEN_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
                //同步请求方式，刷新Token
                refreshToken();
                //再次请求
                return chain.proceed(request);
            }
            return response;
        }
    };

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private static boolean isTokenExpired(Response response) {
        try {
            String json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        if (response.code() == 401) {
            return true;
        }
        return false;
    }

    private static void refreshToken() {
        String token_url = "BASE_URL" + "oauth/token";
        String refresh_token = SpUtils.getString("refresh_token", "");
        RequestBody body = new FormBody.Builder()
                .add("client_id", "mobile-client")
                .add("client_secret", "mobile")
                .add("grant_type", "refresh_token")
                .add("refresh_token", refresh_token).build();
        Request request = new Request.Builder()
                .url(token_url)
                .post(body)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            String tokenJson = response.body().string();
            if (!TextUtils.isEmpty(tokenJson)) {
                //重新写入token
                SpUtils.putString("access_token", "token");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .cookieJar(CookieJar.NO_COOKIES)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(REQUEST_INTERCEPTOR)
                    .addInterceptor(RESPONSE_INTERCEPTOR)
                    .addInterceptor(new HttpLoggingInterceptor(message -> Log.i("http", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cache(CACHE)
                    .build();
        }
        return mOkHttpClient;
    }
}
