package com.flyingogo.flyingterminal.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.flyingogo.flyingterminal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayer extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, View.OnTouchListener {

    @BindView(R.id.video_player)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player2);
        ButterKnife.bind(this);
        initVideoView();
    }

    private void initVideoView() {
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test));
        //设置播放完成以后监听
        videoView.setOnCompletionListener(this);
        //设置发生错误监听，如果不设置videoview会向用户提示发生错误
        videoView.setOnErrorListener(this);
        //设置在视频文件在加载完毕以后的回调函数
        videoView.setOnPreparedListener(this);
        //设置videoView的点击监听
        videoView.setOnTouchListener(this);
        setVideoViewLayoutParams(1);
    }

    /**
     * 设置videiview的全屏和窗口模式
     * @param paramsType 标识 1为全屏模式 2为窗口模式
     */
    public void setVideoViewLayoutParams(int paramsType){

        if(1==paramsType) {
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            videoView.setLayoutParams(LayoutParams);
        }else{
            //动态获取宽高
            DisplayMetrics DisplayMetrics=new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
            int videoHeight = DisplayMetrics.heightPixels - 50;
            int videoWidth = DisplayMetrics.widthPixels - 50;
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth,videoHeight);
            LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            videoView.setLayoutParams(LayoutParams);
        }

    }

    private static final String TAG = "VideoPlayer";
    /**
     * 视频播放发生错误时调用的回调函数
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e("text","发生未知错误");

                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
               Log.e("text","媒体服务器死机");
                break;
            default:
                Log.e("text","onError+"+what);
                break;
        }
        switch (extra){
            case MediaPlayer.MEDIA_ERROR_IO:
                //io读写错误
                Log.e("text","文件或网络相关的IO操作错误");
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                //文件格式不支持
                Log.e("text","比特流编码标准或文件不符合相关规范");
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                //一些操作需要太长时间来完成,通常超过3 - 5秒。
                Log.e("text","操作超时");
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                //比特流编码标准或文件符合相关规范,但媒体框架不支持该功能
                Log.e("text","比特流编码标准或文件符合相关规范,但媒体框架不支持该功能");
                break;
            default:
                Log.e("text","onError+"+extra);
                break;
        }
        //如果未指定回调函数， 或回调函数返回假，VideoView 会通知用户发生了错误。
        return false;
    }
    @Override
    protected void onStart() {
        super.onStart();
        //启动视频播放
        videoView.start();
        //设置获取焦点
        videoView.setFocusable(true);


    }


    /**
     * 页面暂停效果处理
     */
    @Override
    protected  void onPause() {
        super.onPause();
        //如果当前页面暂定则保存当前播放位置，并暂停
        int intPositionWhenPause = videoView.getCurrentPosition();
        //停止回放视频文件
        videoView.stopPlayback();
    }

    /**
     * 页面从暂停中恢复
     */
    @Override
    protected void onResume() {
        super.onResume();
        //跳转到暂停时保存的位置

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=videoView){
            videoView=null;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
