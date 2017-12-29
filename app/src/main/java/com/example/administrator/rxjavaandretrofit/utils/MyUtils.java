package com.example.administrator.rxjavaandretrofit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gaoqiong on 2017/12/27
 */

public class MyUtils {

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
