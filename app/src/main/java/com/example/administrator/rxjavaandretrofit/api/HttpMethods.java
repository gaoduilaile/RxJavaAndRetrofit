package com.example.administrator.rxjavaandretrofit.api;

import android.content.Context;
import android.util.Log;

import com.example.administrator.rxjavaandretrofit.jbean.FeedBackEntity;
import com.example.administrator.rxjavaandretrofit.jbean.LoginClass;
import com.example.administrator.rxjavaandretrofit.jbean.UserInfo;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/13.
 * 网络请求封装类
 */

public class HttpMethods {
    //    public final static String BASE_URL = "http://vision.erunner.cn/";
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;
    private MarketApi mMarketApi;

    //获取单例
    public static HttpMethods getInstance(Context context) {
        return new HttpMethods(context);
    }

    //构造方法私有
    private HttpMethods(Context context) {
        //手动创建一个OkHttpClient
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        ////连接失败后是否重新连接   并设置超时时间
        httpClientBuilder.retryOnConnectionFailure(true).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        //设置拦截器
//        httpClientBuilder.addNetworkInterceptor(new MyInterceptor());
        httpClientBuilder.addNetworkInterceptor(new LoggingInterceptor());

        //设置缓存路径
        File cacheFile = new File(context.getCacheDir(), "cacheData");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        httpClientBuilder.cache(cache);


        //创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        //得到网络接口类
        mMarketApi = retrofit.create(MarketApi.class);

    }


    /**
     * 用于获取豆瓣电影Top250的数据
     *
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovie(int start, int count, Func func) {

        Observable<ResponseBody> observable = mMarketApi.getTopMovie(start, count);

        subscribe(func, observable);
    }

    /*反馈*/
    public void editFeedBack(Subscriber<FeedBackEntity> subscriber, String user_name, String feedback_message) {
        mMarketApi.editFeedBack(user_name, feedback_message)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /*提交用户资料*/
    public void upLoadUserMessage(Subscriber<FeedBackEntity> subscriber, UserInfo info) {
        mMarketApi.upLoadUserMessage(info)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*反馈*/
    public void getUserMessage(Subscriber<FeedBackEntity> subscriber, String user_name, String feedback_message) {
        mMarketApi.editFeedBack(user_name, feedback_message)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


//    /*登录 */
    public  void userLogin(final Context context, String user_name, final Func func) {
        String mobile_system_version = android.os.Build.VERSION.RELEASE;
        LoginClass registerClass = new LoginClass(user_name, 0, mobile_system_version, " ");//根据Bean类初始化一个需要提交的数据类
        Gson gson = new Gson();
        String toJson = gson.toJson(registerClass);////通过Gson将Bean转化为Json字符串形式
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJson);
        Observable<ResponseBody> observable = mMarketApi.newuserlogin(requestBody);
        subscribe(func, observable);
    }


    /*请求成功回调接口*/
    public interface Func {
        void onJsonString(String response) throws JSONException;

        void onError();
    }


    //请求开始

    private void subscribe(final Func func, Observable<ResponseBody> observable) {

        observable.subscribeOn(Schedulers.newThread())//这里需要注意的是，网络请求在非ui线程。如果返回结果是依赖于Rxjava的，则需要变换线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.e("subscribeOn ", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("subscribeOn ", "onError=" + e);
                        func.onError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        try {
                            String string = responseBody.string();
                            Log.e("subscribeOn ", "onNext=" + string);
                            func.onJsonString(string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });


    }

}
