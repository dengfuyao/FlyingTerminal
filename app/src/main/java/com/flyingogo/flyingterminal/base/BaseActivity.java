package com.flyingogo.flyingterminal.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 作者：dfy on 4/7/2017 15:57
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        //setRequestedOrientation(ActivityInfo .SCREEN_ORIENTATION_PORTRAIT);//竖屏
       setContentView(getResLayoutID());
        mContext = getApplicationContext();
        ButterKnife.bind(this);
        onInitMap(savedInstanceState);
        onInit();
    }

    protected void onInit(){};

    protected void onInitMap(Bundle savedInstanceState) {

    }

    protected abstract int getResLayoutID();

    public void toActivity(Class<?> activity){
        Intent intent = new Intent(mContext,activity);
        startActivity(intent);
    }

}
