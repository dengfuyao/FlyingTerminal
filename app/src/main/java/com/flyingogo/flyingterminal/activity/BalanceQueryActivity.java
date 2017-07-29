package com.flyingogo.flyingterminal.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.module.BalanceBean;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 余额查询页面:
 */

public class BalanceQueryActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.activity_balance_query)
    LinearLayout   mActivityBalanceQuery;
    @BindView(R.id.current_balance)
    TextView       mCurrentBalance;
    @BindView(R.id.tx_card_no)
    TextView       mCardNo;
    @BindView(R.id.current_cash_pledge)
    TextView mCurrentCashPledge;

    BalanceBean mBalanceBean;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mCurrentBalance.setText(getResources().getString(R.string.current_balance) +
                    String.valueOf(mBalanceBean.data.cardBalance));

            mCurrentCashPledge.setText(getResources()
                    .getString(R.string.cash_pledge)+String.valueOf(mBalanceBean.data.cardDeposit));

        }
    };


    @Override
    protected int getResLayoutID() {
        return R.layout.activity_balance_query;
    }

    private static final String TAG = "BalanceQueryActivity";

    @Override
    protected void onInit() {
        super.onInit();
        //// TODO: 8/7/2017 发起请求查询余额 5970300233
        String card_no = getIntent().getStringExtra(Contants.CARD_NO);


        mCardNo.setText(getResources().getString(R.string.current_card_no) + card_no);
        String url = null;

        String reg = "^1[3|4|5|7|8][0-9]\\d{8}$";
        boolean matches = card_no.matches(reg);
        if (matches) {
            url = URLUtils.getBalanceMobileUrl(card_no);
        } else {
            url = URLUtils.getBalanceCardUrl(card_no);
        }
        Log.e(TAG, "onInit: url = "+url );
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: 余额查询失败" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: response = " + response);

                Gson gson = new Gson();

                mBalanceBean = gson.fromJson(response.toString(), BalanceBean.class);
                if (mBalanceBean!=null){
                Log.e(TAG, "handleMessage: blance = " + mBalanceBean.data.cardBalance);
                mHandler.sendEmptyMessage(0);}
            }
        });
    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
