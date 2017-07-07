package com.flyingogo.flyingterminal.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 5/7/2017 09:11
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class OtherActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.iv_else_service_guide)
    LinearLayout   mIvElseServiceGuide;
    @BindView(R.id.iv_else_notice)
    LinearLayout   mIvElseNotice;
    @BindView(R.id.iv_else_initial_address)
    LinearLayout   mIvElseInitialAddress;

    @Override
    protected int getResLayoutID() {
        return R.layout.other_activity;
    }

    @Override
    protected void onInit() {

        mBack.setImageResource(R.drawable.activity_title_else);
    }

    @OnClick({R.id.right, R.id.iv_else_service_guide, R.id.iv_else_notice, R.id.iv_else_initial_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                finish();
                break;
            case R.id.iv_else_service_guide:
                break;
            case R.id.iv_else_notice:
                break;
            case R.id.iv_else_initial_address:
                break;
        }
    }
}
