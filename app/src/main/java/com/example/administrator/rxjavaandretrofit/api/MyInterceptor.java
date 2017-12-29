package com.example.administrator.rxjavaandretrofit.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gaoqiong on 2017/12/27
 */


public class  MyInterceptor implements Interceptor ,HttpLoggingInterceptor.Logger {
    @Override
    public Response intercept(Chain chain) throws IOException {


//            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//            cacheBuilder.maxStale(365, TimeUnit.DAYS);
//            CacheControl cacheControl = cacheBuilder.build();


        Request request = chain.request();
//            if (!MyUtils.isNetworkAvailable(MyApplication.mContext)) {
//                request = request.newBuilder()
//                        .cacheControl(cacheControl)
//                        .build();
//
//                Log.d("CacheInterceptor","no network");
//            }

        Response response = chain.proceed(request);

//            if (MyUtils.isNetworkAvailable(MyApplication.mContext)) {
//                int maxAge = 0; // read from cache
//
//                return response.newBuilder()
//                        .header("Cache-Control", "public ,max-age=" + maxAge)
//                        .removeHeader("Pragma")
//                        .build();
//
//
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                return response.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .removeHeader("Pragma")
//                        .build();
//            }

        int maxAge = 60;
        return response.newBuilder()
                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build();

    }

    @Override
    public void log(String message) {
        Log.e("HttpLogInfo", message);
    }
};