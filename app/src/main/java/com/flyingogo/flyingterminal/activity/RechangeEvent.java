package com.flyingogo.flyingterminal.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.module.CardRechargeBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 18/7/2017 15:58
 * <p>  充值页面;
 * 邮箱：dengfuyao@163.com
 */

public class RechangeEvent extends BaseActivity {
    @BindView(R.id.balance)
    TextView  mBalance;
    @BindView(R.id.recharge_cardNo)
    TextView  mRechargeCardNo;
    @BindView(R.id.recharge_type)
    TextView  mRechargeType;
    @BindView(R.id.qr_code)
    ImageView mQrCode;
    @BindView(R.id.amount_payable)
    TextView  mAmountPayable;
    @BindView(R.id.bt_abondon_rechange)
    Button    mBtAbondonRechange;

    @Override
    protected int getResLayoutID() {
        return R.layout.fragment_rechange_event;
    }

    @Override
    protected void onInit() {
        Intent intent = getIntent();
        CardRechargeBean bean = (CardRechargeBean) intent.getSerializableExtra(Contants.CORD_RECHARGE_BEAN);
        mRechargeCardNo.setText(bean.card_no);
        int rechargeType = bean.rechargeType;
        int rechangeAmpunt = bean.rechangeAmpunt;

    }

    @OnClick(R.id.bt_abondon_rechange)
    public void onClick() {
    }
}
