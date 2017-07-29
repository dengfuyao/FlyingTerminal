package com.flyingogo.flyingterminal.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.dialog.RechargeSuccessDialog;
import com.flyingogo.flyingterminal.module.AliPayRechargeBean;
import com.flyingogo.flyingterminal.module.RechargeCardBena;
import com.flyingogo.flyingterminal.module.RechargeResultBean;
import com.flyingogo.flyingterminal.utils.NavigationBarHelp;
import com.flyingogo.flyingterminal.utils.ThreadUtils;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.flyingogo.flyingterminal.weiget.CountDownView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.flyingogo.flyingterminal.R.id.balance;

/**
 * 作者：dfy on 25/7/2017 18:35
 * <p> 二维码界面
 * 邮箱：dengfuyao@163.com
 */

public class RechargeFragment extends FragmentActivity {

    @BindView(R.id.service_tel)
    TextView      mServiceTel;
    @BindView(balance)
    TextView      mBalance;
    @BindView(R.id.recharge_cardNo)
    TextView      mRechargeCardNo;
    @BindView(R.id.recharge_type)
    TextView      mRechargeType;
    @BindView(R.id.qr_code)
    ImageView     mQrCode;
    @BindView(R.id.amount_payable)
    TextView      mAmountPayable;
    @BindView(R.id.bt_abondon_rechange)
    Button        mBtAbondonRechange;
    @BindView(R.id.count_down)
    CountDownView mCountDown;
    private String mWechatOrderid = null, mAliPayTradeNo = null;


    private RechargeResultBean mResultBean;
    private String             orderid;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    /**
     * 获取到点击充值
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rechange_event);
        ButterKnife.bind(this);
        NavigationBarHelp.hideNavigation(this);
        Intent intent = getIntent();
        if (intent != null) {
            int type = intent.getIntExtra(Contants.TYPE_Pay, -1);
            switch (type) {
                case Contants.TYPE_WECHAT:
                    mRechargeType.setText("打开微信扫一扫");
                    RechargeCardBena mRechargeBean = (RechargeCardBena) intent.getSerializableExtra(Contants.CORD_RECHARGE_BEAN);
                    if (mRechargeBean != null)
                        initWechatRecharge(mRechargeBean);
                    break;

                case Contants.TYPE_ALI:
                    mRechargeType.setText("打开支付宝扫一扫");
                    AliPayRechargeBean aliPayRechargeBean = (AliPayRechargeBean) intent.getSerializableExtra(Contants.ALI_REC_BEN);
                    if (aliPayRechargeBean != null) {
                        initAliPayReCharge(aliPayRechargeBean);
                    }
                    break;
            }
            //定时发起查询请求,重复操作,直到页面退出
            ThreadUtils.onRunBigThread(new Runnable() {
                @Override
                public void run() {
                    mRunnable.run();
                }
            });
        }
        mCountDown.setSecond(120);
        mCountDown.beginRun();
        mCountDown.setContDownViewListener(new CountDownView.CountDownViewListener() {
            @Override
            public void onCountDownViewListener() {
               // ToastUtil.show(getApplicationContext(), "操作超时");
                finish();
            }
        });
    }

    private  boolean isRecharge = false; //是否充值成功;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
           a: while (!isRecharge&&mCountDown.getSecound()>5) {
                Log.e(TAG, "run: time = " + mCountDown.getSecound());

                synchronized (this) {
                    if (mWechatOrderid != null) {
                        orderid = mWechatOrderid;
                    } else if (mAliPayTradeNo != null) {
                        orderid = mAliPayTradeNo;
                    }
                    if (orderid != null) {
                        Log.e(TAG, "run: time = " + mCountDown.getSecound());
                        onRechargeResult(orderid);  //发起请求查询充值结果;
                        if (mResultBean != null && mResultBean.msg != null) {

                            ThreadUtils.onRunUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showCardInfoDialog();
                                    mCountDown.setVisibility(View.GONE);
                                    mBalance.setText(mResultBean.cardBalnace+"");
                                    mCountDown.setSecond(5);  //重新设置退出的时间;
                                }
                            });
                            isRecharge = true;
                            orderid = null;
                        }

                    }

                }
               Log.e(TAG, "run: isrecharge = " +isRecharge );
               if(!isRecharge)
                   SystemClock.sleep(5000);
            }
        }

    };

    private static final String TAG = "RechargeFragment";

    private void onRechargeResult(String orderId) {

        String url = URLUtils.getWeCharResultUrl(orderId);

        OkHttpUtils.get().url(url).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: response = " + response);
                        Gson gson = new Gson();
                        mResultBean = gson.fromJson(response.toString(), RechargeResultBean.class);

                    }

                });
    }

    private void initAliPayReCharge(AliPayRechargeBean bean) {
        mRechargeCardNo.setText(bean.cardNo);
        mAmountPayable.setText(bean.rechargeAmount);
        mBalance.setText(bean.resultType.message);
        mQrCode.setImageBitmap(getBitmap(bean.imgurl));
        mAliPayTradeNo = bean.outTradeNo;
    }

    private void initWechatRecharge(RechargeCardBena mRechargeBean) {

        List<RechargeCardBena.DataBean> data = mRechargeBean.data;
        for (RechargeCardBena.DataBean bean : data
                ) {
            mRechargeCardNo.setText(bean.cardNo);
            mAmountPayable.setText(bean.rechargeAmount);
            RechargeCardBena.DataBean.ResultTypeBean resultType = bean.resultType;
            mWechatOrderid = bean.orderid;
            mBalance.setText(resultType.message);
            //// TODO: 25/7/2017 加载二维码;
            Bitmap bitmap = getBitmap(bean.imgurl);
            mQrCode.setImageBitmap(bitmap);
            //Glide.with(this).load(imgurl).into(mQrCode);
        }
    }

    private Bitmap getBitmap(String url) {

        byte[] byt = (Base64.decode(url.getBytes(), 0));

        return BitmapFactory.decodeByteArray(byt, 0, byt.length);
    }

    @OnClick({R.id.bt_abondon_rechange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_abondon_rechange:
                finish();
                break;
        }
    }

    private void showCardInfoDialog() {
        RechargeSuccessDialog dialog = new RechargeSuccessDialog(this);
        Window window = dialog.getWindow();
        dialog.upData(mResultBean.msg, mResultBean.cardBalnace + "");
        window.setGravity(Gravity.CENTER);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.70); //宽度设置为屏幕
        p.height = (int) (d.getHeight() * 0.60);
        dialog.getWindow().setAttributes(p); //设置生效
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        mCountDown.stopRun();
        super.onDestroy();
    }
}
