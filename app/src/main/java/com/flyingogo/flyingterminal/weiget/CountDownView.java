package com.flyingogo.flyingterminal.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者：dfy on 26/7/2017 14:34
 * <p> 倒计时控件;
 * 邮箱：dengfuyao@163.com
 */

public class CountDownView extends TextView implements Runnable {


    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    private static long secound ;
    private long mSecond;
    @Override
    public void run() {

        if (run) {
            mSecond--;
            secound = mSecond;
            if (mSecond < 0) {

               // setText("开始倒计时");
                stopRun();
                if (mListener!=null) {
                    mListener.onCountDownViewListener();
                }
            } else {
                setText(mSecond + "秒");
                postDelayed(this, 1000);
            }
        } else {
            removeCallbacks(this);
        }
    }

    public long getSecound(){
        return secound;
    }

    /**
     * 设置倒计时长
     *
     * @param second 秒
     */
    public void setSecond(long second) {
        this.mSecond = second;
    }

    private boolean run = false;

    public boolean isRun() {
        return run;
    }

    public void beginRun() {
        this.run = true;
        setClickable(false);
        run();
    }

    public void stopRun() {
        this.run = false;
        setClickable(true);
    }
    private CountDownViewListener mListener;

public interface CountDownViewListener {
   void  onCountDownViewListener();
 }

    public CountDownViewListener setContDownViewListener(CountDownViewListener Listener){
        mListener = Listener;
        return mListener;
    }

}
