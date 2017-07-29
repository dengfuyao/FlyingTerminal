package com.flyingogo.flyingterminal.activity;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.utils.NavigationBarHelp;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 4/7/2017 09:12
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.image_icon)
    ImageView      mImageIcon;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.ll_refer_station)
    LinearLayout   mLlReferStation;
    @BindView(R.id.ll_balance_card)
    LinearLayout   mBalanceCard;
    @BindView(R.id.ll_rechange_centre)
    LinearLayout   mRechangeCentre;
    @BindView(R.id.ll_other)
    LinearLayout   mLlElse;
    @BindView(R.id.tv_control_num)
    TextView       mTvControlNum;
    @BindView(R.id.tv_control_celsius)
    TextView       mTvControlCelsius;
    @BindView(R.id.tv_control_tension)
    TextView       mTvControlTension;
    @BindView(R.id.ll_news)
    LinearLayout   mLlNews;
    @BindView(R.id.activity_main)
    LinearLayout   mActivityMain;
    @BindView(R.id.logout)
    Button         mLogout;
    @BindView(R.id.text_time)
    TextView       mTextTime;

    private GoogleApiClient mClient;


    @Override
    protected int getResLayoutID() {
        return R.layout.activity_main;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onInit() {
        boolean b = NavigationBarHelp.hasNavigationBar(mContext);

        NavigationBarHelp.hideNavigation(MainActivity.this);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = df.format(date);
        Log.e(TAG, "onInit: time = " + format);
        new TimeThread().start();
    }

    @OnClick({R.id.ll_refer_station, R.id.ll_balance_card, R.id.ll_rechange_centre, R.id.ll_other, R.id.logout,R.id.activity_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_refer_station:  //站点查询
                toActivity(StationInforMationActivity.class);
                //toActivity(Assistant_Location_Activity.class);//用webview加载地图,没有注册web的key,所以不能使用
                break;
            case R.id.ll_balance_card:    //用户卡查询
                toActivity(UserCardActivity.class);
                break;
            case R.id.ll_rechange_centre:  //充值中心;
                toActivity(RechargeCenterActivity.class);
                // TODO: 26/7/2017 连接单片机时读卡
                //  toActivity(ComAssistantActivity.class);
                break;
            case R.id.ll_other:         //其他服务
                toActivity(OtherServerActivity.class);
                break;
            case R.id.logout:
                mLogout.setVisibility(View.INVISIBLE);
                finish();
                break;
            case R.id.activity_main:
               // if ()

        }
    }

    private              boolean flag      = true;
    private static final int     WHAT_BACK = 1;
    private              Handler handler   = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_BACK:
                    flag = false;
                    break;
            }
        }
    };

    private static final int msgKey1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }

    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEE");
                    mTextTime.setText(format.format(date));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK & flag) {
            Toast.makeText(MainActivity.this, "双击按钮退出应用", Toast.LENGTH_LONG).show();
            flag = false;
            handler.sendEmptyMessageDelayed(WHAT_BACK, 2000);
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    private int count = 0;

}
