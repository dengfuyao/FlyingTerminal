package com.flyingogo.flyingterminal.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.popwindow.SelectPopupWindow;
import com.flyingogo.flyingterminal.utils.NavigationBarHelp;
import com.flyingogo.flyingterminal.utils.ThreadUtils;
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

public class MainActivity extends BaseActivity implements SelectPopupWindow.OnPopWindowClickListener{
    private SelectPopupWindow menuWindow;
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
    @BindView(R.id.text_time)
    TextView       mTextTime;
    @BindView(R.id.logout)
    ImageView      mLogout;

    private GoogleApiClient mClient;
    private LocalBroadcastManager mLocalBroadcastManager;
    private IntentFilter mIntentFilter;



    @Override
    protected int getResLayoutID() {
        return R.layout.activity_main;
    }



    //打开输入密码的对话框
    public void inoutPsw(){

        if (menuWindow==null)
        menuWindow = new SelectPopupWindow(this, this);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
        ThreadUtils.onRunUiThread(new Runnable() {
            @Override
            public void run() {
                NavigationBarHelp.hideNavigation(MainActivity.this);
            }
        });
    }
    @Override
    public void onPopWindowClickListener(String psw, boolean complete) {
        if(complete){
            if (psw.toString().trim().equalsIgnoreCase("666666")){
                finish();
            }
            Toast.makeText(this, "密码不正确"+psw, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: main获取焦点" );
        if (mLocalBroadcastManager==null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        }
        mLocalBroadcastManager.registerReceiver(serialReceiver, getIntentFilter());
        super.onResume();
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

    private BroadcastReceiver serialReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "onReceive: action = "+action );
          if (action.equalsIgnoreCase(Contants.Action.TEMPERATURE)){
              final String temperatures = intent.getStringExtra(Contants.TEMPERATURE);
              Log.e(TAG, "onReceive: temperature = "+temperatures );
              final int temperature = Integer.parseInt(temperatures, 16);
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      mTvControlCelsius.setText(temperature+"℃");
                  }
              });
          }
        }
    };

    @OnClick({R.id.ll_refer_station, R.id.ll_balance_card, R.id.ll_rechange_centre, R.id.ll_other, R.id.logout, R.id.activity_main})
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
              //  toActivity(RechargeCenterActivity.class);
                // TODO: 26/7/2017 连接单片机时读卡
               //  toActivity(ComAssistantActivity.class);
                toActivity(RechargerActivity.class);
                break;
            case R.id.ll_other:         //其他服务
                toActivity(ServerCenterActivity.class);
                break;
            case R.id.logout:
                NavigationBarHelp.hideNavigation(this);
                inoutPsw();

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

    public IntentFilter getIntentFilter() {
        if (mIntentFilter==null) {
            mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(Contants.Action.TEMPERATURE);
        }
        return mIntentFilter;
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(serialReceiver);
        super.onDestroy();
    }
}
