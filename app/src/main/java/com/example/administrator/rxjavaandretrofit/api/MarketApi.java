package com.example.administrator.rxjavaandretrofit.api;

import com.example.administrator.rxjavaandretrofit.jbean.MovieEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/13.
 * retrofit 接口类
 */

public interface MarketApi {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
