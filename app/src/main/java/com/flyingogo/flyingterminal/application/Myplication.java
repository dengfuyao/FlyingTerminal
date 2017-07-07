package com.flyingogo.flyingterminal.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * 作者：dfy on 24/6/2017 14:44
 * <p>
 * 邮箱：dengfuyao@163.com
 */
public class Myplication extends Application {
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
