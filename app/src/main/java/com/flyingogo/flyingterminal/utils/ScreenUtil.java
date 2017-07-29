package com.flyingogo.flyingterminal.utils;

import android.content.Context;
import android.view.WindowManager;

import com.flyingogo.flyingterminal.application.Myplication;

/**
 * 作者：dfy on 27/7/2017 12:27
 * <p>
 * 邮箱：dengfuyao@163.com
 */
public class ScreenUtil {
    public static int sysWidth()
    {
        WindowManager wm = (WindowManager) Myplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width/2;
    }
    public static int sysHeight()
    {
        WindowManager wm = (WindowManager) Myplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height/2;
    }
}
