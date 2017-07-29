package com.flyingogo.flyingterminal.activity;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.utils.NavigationBarHelp;
import com.flyingogo.flyingterminal.utils.ThreadUtils;

/**
 * 作者：dfy on 18/7/2017 14:17
 * <p> 视频播放
 * 邮箱：dengfuyao@163.com
 */
public class VideoPlayerActivity extends AppCompatActivity implements MediaPlayer.OnErrorListener, SurfaceHolder.Callback {

    private MediaPlayer   mMediaPlayer;
    private SurfaceView   mSurfaceView;
    private SurfaceHolder mHolder;

    private static final String TAG = "VideoPlayerActivity";
    private TextView mCurrent_index;
    private Uri      mUri;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView)
        ;
     //mUrl = "http://www.nnggzxc.com/upload/vido/ridelybike.mp4";
     mUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ridelybike);
        setAudio();
        playerVideo();
    }

    private void setAudio() {


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);


                 //设置静音模式;
                   audioManager.setStreamMute(AudioManager.STREAM_MUSIC , true);


                  //启动声音调节默认有声音;
                    //audioManager.setStreamMute(AudioManager.STREAM_MUSIC , false);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void playerVideo() {
         ThreadUtils.onRunBigThread(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer == null) {
                    // mMediaPlayer = MediaPlayer.create(VideoPlayerActivity.this, R.raw.test2);
                    Log.e(TAG, "run: 创建对象" );
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setOnErrorListener(VideoPlayerActivity.this);
                } else {
                  //  mMediaPlayer.reset();
                }
                try {mMediaPlayer.setDataSource(VideoPlayerActivity.this,mUri);
                  //  mMediaPlayer.setDataSource(mUrl);
                    mHolder = mSurfaceView.getHolder();
                    mHolder.addCallback(VideoPlayerActivity.this);
                    // 设置播放时打开屏幕
                    mHolder.setKeepScreenOn(true);

                    mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.prepareAsync(); //异步加载视屏
                    Log.e(TAG, "run: 1111111111");
                    mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer
                            .OnVideoSizeChangedListener() {
                        @Override
                        public void
                        onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            Log.e(TAG, "onVideoSizeChanged: ...." + width + "..." + height + "..." + mMediaPlayer.getVideoWidth() + "..." + mMediaPlayer
                                    .getVideoHeight());
                            updateVideoViewSize(mMediaPlayer.getVideoWidth(), mMediaPlayer
                                    .getVideoHeight());
                        }
                    });
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            mMediaPlayer.setDisplay(mHolder);

                            mMediaPlayer.start();
                        }
                    });

                    mMediaPlayer.setLooping(true);
                    boolean playing = mMediaPlayer.isPlaying();
                    Log.e(TAG, "run: playing = " + playing);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void back() {
        finish();
    }

    /**
     * TextureView resize
     */
    public void updateVideoViewSize(float videoWidth, float videoHeight) {
        if (videoWidth > videoHeight) {
            RelativeLayout.LayoutParams videoViewParam;
            int height = (int) ((videoHeight / videoWidth) * mSurfaceView.getWidth());
            videoViewParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    height);
            videoViewParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout
                    .TRUE);
            mSurfaceView.setLayoutParams(videoViewParam);
        }
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {
        NavigationBarHelp.hideNavigation(VideoPlayerActivity.this);
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                    mSurfaceView = null;
                }
                this.finish();


        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            onDestroy();
        }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Log.e(TAG, "onError: what == "+ what );
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}