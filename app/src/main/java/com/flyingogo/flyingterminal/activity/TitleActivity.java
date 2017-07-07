package com.flyingogo.flyingterminal.activity;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 5/7/2017 11:35
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class TitleActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_title_info;
    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
