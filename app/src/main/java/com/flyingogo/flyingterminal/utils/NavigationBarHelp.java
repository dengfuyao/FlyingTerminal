package com.flyingogo.flyingterminal.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 作者：dfy on 18/7/2017 21:21
 * <p>  隐藏导航键
 * 邮箱：dengfuyao@163.com
 */

public class NavigationBarHelp {
    /**
     * 是否有虚拟键
     *
     * @return
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasMenuKey = true, hasBackKey = true;
        boolean ret = false;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
                hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            }

            if ((!hasMenuKey) && (!hasBackKey)) {
                ret = true;
            }
        } catch (Exception e) {
            ret = false;
        }

        return ret;
    }
    /**
     * 隐藏虚拟键
     */
    public static void hideNavigation(Activity context) {

        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)) {
          //  Logger.get().d("myth hideNavigation  " + context.getClass().getSimpleName());

            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    /**
     * 隐藏虚拟键
     */
    public static void hideNavigation(View view) {

        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)) {
          //  Logger.get().d("myth hideNavigation  " + view.getClass().getSimpleName());

//            | View.SYSTEM_UI_FLAG_FULLSCREEN

            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    /**
     * 显示虚拟键
     */
    public static void showNavigation(View view) {

        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)) {
           // Logger.get().d("myth hideNavigation  " + view.getClass().getSimpleName());

            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }



}
