package com.flyingogo.flyingterminal.activity;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 4/7/2017 09:12
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.image_icon)
    ImageView      mImageIcon;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.ll_refer_station)
    LinearLayout   mLlReferStation;
    @BindView(R.id.ll_balance_card)
    LinearLayout   mBalanceCard;
    @BindView(R.id.ll_abnormal_card)
    LinearLayout   mLlAbnormalCard;
    @BindView(R.id.ll_other)
    LinearLayout   mLlElse;
    @BindView(R.id.rl_tv_title)
    TextView       mRlTvTitle;
    @BindView(R.id.et_main_card)
    EditText       mEtMainCard;
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

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.ll_refer_station, R.id.ll_balance_card, R.id.ll_abnormal_card, R.id.ll_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_refer_station:  //站点查询
              toActivity(StationInforMationActivity.class);
                //toActivity(Assistant_Location_Activity.class);//用webview加载地图,没有注册web的key,所以不能使用
                break;
            case R.id.ll_balance_card:    //余额卡查询
               toActivity(BalanceCardActivity.class);
                break;
            case R.id.ll_abnormal_card:  //卡异常处理
               toActivity(AbnormalActivity.class);
                break;
            case R.id.ll_other:         //其他服务
                toActivity(OtherActivity.class);
                break;
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK & flag) {
            Toast.makeText(MainActivity.this, "双击按钮退出应用", Toast.LENGTH_LONG).show();
            flag = false;
            handler.sendEmptyMessageDelayed(WHAT_BACK, 2000);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
