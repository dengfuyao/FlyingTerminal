package com.flyingogo.flyingterminal.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 5/7/2017 09:50
 * <p> 卡的异常处理
 * 邮箱：dengfuyao@163.com
 */

public class AbnormalActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.et_abnnormal_card)
    EditText       mEtAbnnormalCard;

    @Override
    protected int getResLayoutID() {
        return R.layout.abnormal_activity;
    }

    @Override
    protected void onInit() {
        mBack.setImageResource(R.drawable.activity_title_abnormal);

    }


    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
