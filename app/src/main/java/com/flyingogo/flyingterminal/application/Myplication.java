package com.flyingogo.flyingterminal.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;

import com.flyingogo.flyingterminal.Exception.CrashHandlerRestart;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 作者：dfy on 24/6/2017 14:44
 * <p>
 * 邮箱：dengfuyao@163.com
 */
public class Myplication extends Application {
    private static Myplication myApplication;
    public static Myplication getInstance() {
        return myApplication;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpUtils();


    }

    private void initOkHttpUtils() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

    private static final int      REQUEST_EXTERNAL_STORAGE = 0;
    private static       String[] PERMISSIONS_STORAGE      = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,  //辨别设备信息
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//读写
            Manifest.permission.READ_LOGS,//读取日志;
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
      /*  int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);*/

        int permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_STORAGE[0]);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);

        }
    }

}
