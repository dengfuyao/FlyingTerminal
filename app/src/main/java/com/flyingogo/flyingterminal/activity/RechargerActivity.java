package com.flyingogo.flyingterminal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.weiget.CountDownView;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargerActivity extends BaseActivity {


    @BindView(R.id.right)
    TextView      mRight;
    @BindView(R.id.put_in_countdownview)
    CountDownView mPutInCountdownview;

    private  String tag = "";
    private LocalBroadcastManager mInstance;
    private IntentFilter mIntentFilter = null;
    boolean isFast = true;
    @Override
    protected int getResLayoutID() {
        Log.e(TAG, "getResLayoutID: 创建读卡界面" );
        return R.layout.activity_recharger;
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String cardNo = (String) msg.obj;

            if (cardNo!= null && ! tag.equalsIgnoreCase(cardNo)){

                if (isFast) {
                    Log.e(TAG, "handleMessage: obj = " + cardNo);
                    Log.e(TAG, "handleMessage: tag = " + tag);
                    toActivity(RechargeCenterActivity.class, Contants.CARD_NO, cardNo);
                    tag = cardNo;
                    isFast= false;
                    finish();
                }

            }

            super.handleMessage(msg);
        }
    };
    @Override
    protected void onInit() {


        initCountDownView();

        super.onInit();
    }

    private void initCountDownView() {
        mPutInCountdownview.setSecond(60);
        mPutInCountdownview.beginRun();
        mPutInCountdownview.setContDownViewListener(new CountDownView.CountDownViewListener() {
            @Override
            public void onCountDownViewListener() {
                finish();
            }
        });

    }

    private boolean mReceiverTag = false;
    private void registerReceiver() {
        if (!mReceiverTag) {
            if (mInstance==null) {
                mInstance = LocalBroadcastManager.getInstance(this);
            }if (mIntentFilter==null) {
                mIntentFilter = new IntentFilter();
                mIntentFilter.addAction(Contants.Action.CARD_NO);
            }
            mReceiverTag = true;    //标识值 赋值为 true 表示广播已被注册
            mInstance.registerReceiver(receiver, mIntentFilter);

        }
    }

    private static final String TAG = "RechargerActivity";
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(Contants.Action.CARD_NO)){
                final String  cardNo = intent.getStringExtra(Contants.CARD_NO);
                Log.e(TAG, "onReceive: card = "+ cardNo );
                Message message = Message.obtain();
                message.obj = cardNo;
                mHandler.sendMessage(message);
            }
        }
    };

    @Override
    protected void onResume() {
        registerReceiver();
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mReceiverTag)
            Log.e(TAG, "onDestroy: 销毁读卡界面" );
        super.onDestroy();
    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
