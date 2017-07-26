package com.flyingogo.flyingterminal.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.weiget.CardInfoDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：dfy on 5/7/2017 09:25
 * <p> 用户卡查询模块;
 * 邮箱：dengfuyao@163.com
 */

public class UserCardActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.iv_cardinfo_ramaining)
    LinearLayout   mIvCardinfoRamaining;
    @BindView(R.id.iv_cardinfo_rading)
    LinearLayout   mIvCardinfoRading;
    @BindView(R.id.rl_tv_title)
    TextView       mRlTvTitle;
    @BindView(R.id.et_main_card)
    EditText       mEtMainCard;
    @BindView(R.id.login)
    Button         mLogin;
    @BindView(R.id.ll_exception_handling)
    LinearLayout   mLlExceptionHandling;
    @BindView(R.id.loginOut)
    Button         mLoginOut;
    private boolean isLogin = false;  //用来记录是否有插入此卡;
    private static String mCard_no;

    @Override
    protected int getResLayoutID() {
        return R.layout.balance_card_activity;
    }

    @OnClick({R.id.right,R.id.loginOut, R.id.iv_cardinfo_ramaining, R.id.ll_exception_handling, R.id.iv_cardinfo_rading, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:   //返回按钮
                finish();
                break;
            case R.id.loginOut:   //返回按钮

                break;
            case R.id.iv_cardinfo_ramaining:   //余额查询

                if (!isLogin) {
                    showCardInfoDialog();
                } else {
                    Toast.makeText(mContext, "查询余额", Toast.LENGTH_SHORT).show();
                    toActivity(BalanceQueryActivity.class, Contants.CARD_NO, mCard_no);
                }
                break;
            case R.id.iv_cardinfo_rading:      //骑行记录

                if (!isLogin) {
                    showCardInfoDialog();
                } else {
                    Toast.makeText(mContext, "骑行记录", Toast.LENGTH_SHORT).show();
                    toActivity(RideRecordActivity.class, Contants.CARD_NO, mCard_no);  //跳转到查询页面
                }
                break;
            case R.id.login:

                mCard_no = mEtMainCard.getText().toString().trim();
                if (TextUtils.isEmpty(mCard_no)) {
                    Toast.makeText(mContext, "请输入你的卡号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mCard_no.length() < 10) {
                    Toast.makeText(mContext, "输入的卡号不正确,请从新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                isLogin = true;
                hideView(mEtMainCard);
                hideView(mLogin);

                break;
            case R.id.ll_exception_handling:
                if (isLogin) {
                    toActivity(CardManageActivity.class, Contants.CARD_NO, mCard_no);
                } else {
                    showCardInfoDialog();
                }
        }
    }

    private void showCardInfoDialog() {
        CardInfoDialog dialog = new CardInfoDialog(UserCardActivity.this);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.70); //宽度设置为屏幕
        p.height = (int) (d.getHeight() * 0.60);
        dialog.getWindow().setAttributes(p); //设置生效
        dialog.show();
    }

    private void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    private void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}