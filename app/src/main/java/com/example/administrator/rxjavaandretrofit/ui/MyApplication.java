package com.example.administrator.rxjavaandretrofit.ui;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by gaoqiong on 2017/12/27
 */

public class MyApplication extends MultiDexApplication {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
