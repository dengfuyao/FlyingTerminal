package com.flyingogo.flyingterminal.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.weiget.CountDownView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：dfy on 11/7/2017 11:15
 * <p>  异常卡处理的弹框;收到服务器的返回数据就弹出框;
 * 邮箱：dengfuyao@163.com
 */

public class RechargeSuccessDialog extends Dialog {


    @BindView(R.id.manage_result)
    TextView      mManageResult;
    @BindView(R.id.recharge_balance)
    TextView      mRechargeBalance;
    @BindView(R.id.count_down_view)
    CountDownView mCountDownView;
    private Context mContext;

    public RechargeSuccessDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.mContext = context;
        initView(context);
        mCountDownView.setSecond(4);
        mCountDownView.beginRun();
        cleanFocus();  //设置dialog失去焦点;
        mCountDownView.setContDownViewListener(new CountDownView.CountDownViewListener() {
            @Override
            public void onCountDownViewListener() {
                onDismiss();
            }
        });
    }

    private void cleanFocus() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        this.setCanceledOnTouchOutside(true);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_recharge, null);
        setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, this);
//        bt_Sub = (Button) findViewById(R.id.cardinfo_submern);
    }
    public  void upData(String result,String balance){
        mManageResult.setText(result);
        mRechargeBalance.setText("余额 : "+ balance + "元");
    }
    public void noNetwork() {
        mManageResult.setText("网络异常,请重新进入");
    }

    public void onDismiss() {
        if (mCountDownView!= null){
            mCountDownView.stopRun();
            mCountDownView =null;
        }
        dismiss();
    }

}
