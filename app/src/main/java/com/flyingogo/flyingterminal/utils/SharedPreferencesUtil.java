package com.flyingogo.flyingterminal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 作者：dfy on 26/7/2017 22:24
 * <p> SharedPreferences工具类
 * 邮箱：dengfuyao@163.com
 */

public class SharedPreferencesUtil {
    private SharedPreferences            sharedPreferences;
    private static SharedPreferencesUtil sp;

    private SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences("CIRCLEDEMO", Activity.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (null == sp) {
            sp = new SharedPreferencesUtil(context);

        }
        return sp;
    }

    public String getString(String key) {
        if (null == sharedPreferences) {
            return "";
        }
        String result = sharedPreferences.getString(key, "");
        if (!TextUtils.isEmpty(result)) {
            return result;
        }
        return "";
    }

    public void setString(String key, String value) {
        if (null != sharedPreferences) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public void remove(String... key) {
        for (String k : key) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(k);
            editor.commit();
        }
    }

    public void setInt(String key,int value){
        if (null != sharedPreferences) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }
     public int getInt(String key){
         int anInt = sharedPreferences.getInt(key, 0);
         return anInt;
     }

}
