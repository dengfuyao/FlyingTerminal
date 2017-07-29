package com.flyingogo.flyingterminal.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.fragment.RechargeFragment;
import com.flyingogo.flyingterminal.module.AliPayRechargeBean;
import com.flyingogo.flyingterminal.module.RechargeCardBena;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 作者：dfy on 17/7/2017 11:14
 * <p>  充值中心;
 * 通过硬件接口读取用户卡的信息,比如卡的ID号,用户选择充值模式和充值金额,将数据反馈给后台;后台拿到数据进行充值操作,
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class RechargeCenterActivity extends BaseActivity {
    @BindView(R.id.alipay50)
    Button         mAlipay50;
    @BindView(R.id.alipay100)
    Button         mAlipay100;
    @BindView(R.id.alipay200)
    Button         mAlipay200;
    @BindView(R.id.alipay300)
    Button         mAlipay300;
    @BindView(R.id.weichatpay50)
    Button         mWeichatpay50;
    @BindView(R.id.weichatpay100)
    Button         mWeichatpay100;
    @BindView(R.id.weichatpay200)
    Button         mWeichatpay200;
    @BindView(R.id.weichatpay300)
    Button         mWeichatpay300;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.progreess_bar)
    ProgressBar    mProgreessBar;
    @BindView(R.id.rl_progreess)
    RelativeLayout mRlProgreess;
    private String mCardUid = "5970100007";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private String              setCookie;
    private String[]            mCookies;
    private Map<String, String> mHeader;
    private String              mCard_no;

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void onInit() {
        super.onInit();
        Intent intent = getIntent();
        mCard_no = intent.getStringExtra(Contants.CARD_NO);
        Log.e(TAG, "onInit: 卡号==" + mCard_no);

    }

    @OnClick({R.id.right, R.id.alipay50, R.id.alipay100, R.id.alipay200, R.id.alipay300, R.id.weichatpay50, R.id.weichatpay100, R.id.weichatpay200, R.id.weichatpay300})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                finish();

                break;
            case R.id.alipay50:
                requesRecharge(getAliPayUrl(mCardUid,Contants.TYPE_ALI,0.01),Contants.TYPE_ALI);
                break;
            case R.id.alipay100:
                requesRecharge(getAliPayUrl(mCardUid,Contants.TYPE_ALI,100),Contants.TYPE_ALI);
                break;
            case R.id.alipay200:
                requesRecharge(getAliPayUrl(mCardUid,Contants.TYPE_ALI,200),Contants.TYPE_ALI);
                break;
            case R.id.alipay300:
                requesRecharge(getAliPayUrl(mCardUid,Contants.TYPE_ALI,300),Contants.TYPE_ALI);
                break;
            case R.id.weichatpay50:

                requesRecharge(getWeichatUrl(mCardUid, Contants.TYPE_WECHAT,0.01), Contants.TYPE_WECHAT);
                break;
            case R.id.weichatpay100:
                requesRecharge(getWeichatUrl(mCardUid, Contants.TYPE_WECHAT,100), Contants.TYPE_WECHAT);
                break;
            case R.id.weichatpay200:
                requesRecharge(getWeichatUrl(mCardUid, Contants.TYPE_WECHAT,200), Contants.TYPE_WECHAT);
                break;
            case R.id.weichatpay300:
                requesRecharge(getWeichatUrl(mCardUid, Contants.TYPE_WECHAT,300), Contants.TYPE_WECHAT);
                break;
        }
    }

    private static final String TAG = "RechargeCenterActivity";

    private String getWeichatUrl(String cardUid,  int type,double money){
        return URLUtils.getWeiChatRechargeURL(cardUid, money, type);
    }

    private String getAliPayUrl(String cardUid,  int type,double money){
        return URLUtils.getAliPayRechargeURL(cardUid, money, type);
    }
    private void requesRecharge(String url, final int type) {
            mRlProgreess.setVisibility(View.VISIBLE);
        Log.e(TAG, "requesRecharge: uri==" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError:  = " + e.getMessage());
                mRlProgreess.setVisibility(View.GONE);
                toActivity(RechargeFragment.class);
                finish();
                Toast.makeText(mContext, "网络错误,请重新操作!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse:  = " + response.toString());
                Gson gson = new Gson();
                switch (type){
                    case Contants.TYPE_ALI:
                        AliPayRechargeBean aliPayRechargeBean = gson.fromJson(response.toString(), AliPayRechargeBean.class);
                        go2Activity(RechargeFragment.class,aliPayRechargeBean,Contants.ALI_REC_BEN,type);
                        finish();

                        break;
                    case  Contants.TYPE_WECHAT:
                        final RechargeCardBena rechargeCardBena = gson.fromJson(response.toString(), RechargeCardBena.class);
                        //List<RechargeCardBena.DataBean> data = rechargeCardBena.data;
                        if (rechargeCardBena != null) {
                                    go2Activity(RechargeFragment.class,rechargeCardBena,Contants.CORD_RECHARGE_BEAN,type);
                                    finish();
                                }

                        break;
                }


                }

        });
    }

   /*
*/

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RechargeCenter Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
