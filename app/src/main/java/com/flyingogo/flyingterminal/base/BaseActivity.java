package com.flyingogo.flyingterminal.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.flyingogo.flyingterminal.activity.VideoPlayerActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.service.SerialPortService;
import com.flyingogo.flyingterminal.utils.NavigationBarHelp;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * 作者：dfy on 4/7/2017 15:57
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    public Handler handler = new Handler();
    private boolean mIstask;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

     /*   Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);*/


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        //setRequestedOrientation(ActivityInfo .SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(getResLayoutID());
        mContext = getApplicationContext();
        ButterKnife.bind(this);
        onInitMap(savedInstanceState);
        onInit();
        mIntent = new Intent(this, SerialPortService.class);
        startService(mIntent);
    }

    @Override
    protected void onDestroy() {
        if (mIntent!=null)
        stopService(mIntent);
        super.onDestroy();
    }

    protected void onInit() {
        //NavigationBarHelp.hideNavigation(BaseActivity.this);
    }

    ;

    protected void onInitMap(Bundle savedInstanceState) {

    }

    protected abstract int getResLayoutID();


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.e(TAG, "onResume: 开始任务");
        NavigationBarHelp.hideNavigation(BaseActivity.this);
        handler.postDelayed(runnable, 5000 * 60);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        handler.removeCallbacks(runnable);
        mIstask = false;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 用户5秒没操作了
            mIstask = true;
            Intent i = new Intent();
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setClass(mContext, VideoPlayerActivity.class);
            mContext.startActivity(i);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        NavigationBarHelp.hideNavigation(BaseActivity.this);
        handler.removeCallbacks(runnable);
        mIstask = false;

        return super.dispatchTouchEvent(ev);
    }


    private static final String TAG = "BaseActivity";

    public boolean onTouchEvent(android.view.MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: { // 手指下来的时候,取消之前绑定的Runnable
                Log.e(TAG, "onTouchEvent: 清除任务");
                handler.removeCallbacks(runnable);
                mIstask = false;
                break;
            }
            case MotionEvent.ACTION_UP: { // 手指离开屏幕，发送延迟消息 ，5秒后执行
                Log.e(TAG, "onTouchEvent: 开始任务");
                handler.postDelayed(runnable, 5000 * 60);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //检查是否有导航键,有就屏蔽;
            }
        }
        //屏蔽导航键;

        //   boolean b = NavigationBarHelp.hasNavigationBar(BaseActivity.this);
        //  if (!b){
        NavigationBarHelp.hideNavigation(BaseActivity.this);
        //   }
        return super.onTouchEvent(event);
    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        Log.e(TAG, "setFinishOnTouchOutside: 调用屏蔽");
        NavigationBarHelp.hideNavigation(BaseActivity.this);
        super.setFinishOnTouchOutside(finish);
    }

    public void toActivity(Class<?> activity) {
        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
    }

    public void toActivity(Class<?> activity, String key, String value) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void go2Activity(Class<?> activity, Serializable serializable, String key, int type) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(Contants.TYPE_Pay, type);
        intent.putExtra(key, serializable);
        startActivity(intent);

    }

}
