package com.flyingogo.flyingterminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.module.RechargeCardBena;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：dfy on 25/7/2017 18:35
 * <p> 二维码界面
 * 邮箱：dengfuyao@163.com
 */

public class RechargeFragment extends FragmentActivity {


    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.service_tel)
    TextView       mServiceTel;
    @BindView(R.id.balance)
    TextView       mBalance;
    @BindView(R.id.recharge_cardNo)
    TextView       mRechargeCardNo;
    @BindView(R.id.recharge_type)
    TextView       mRechargeType;
    @BindView(R.id.qr_code)
    ImageView      mQrCode;
    @BindView(R.id.amount_payable)
    TextView       mAmountPayable;
    @BindView(R.id.bt_abondon_rechange)
    Button         mBtAbondonRechange;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rechange_event);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        RechargeCardBena   mRechargeBean = (RechargeCardBena) intent.getSerializableExtra(Contants.CORD_RECHARGE_BEAN);
        initView(mRechargeBean);


    }

    private void initView(RechargeCardBena mRechargeBean) {
        List<RechargeCardBena.DataBean> data = mRechargeBean.data;
        for (RechargeCardBena.DataBean bean:data
             ) {
           mRechargeCardNo.setText(bean.cardNo);
            mAmountPayable.setText(bean.rechargeAmount);
            //// TODO: 25/7/2017 加载二维码;
        }

    }

    @OnClick({R.id.right, R.id.bt_abondon_rechange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                break;
            case R.id.bt_abondon_rechange:
                break;
        }
    }
}
