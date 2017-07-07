package com.flyingogo.flyingterminal.activity;

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 7/7/2017 10:40
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class RideRecordActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView       mTitle;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.lv_ride_recode)
    ListView       mLvRideRecode;

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_ride_record;
    }

    @Override
    protected void onInit() {
        super.onInit();
        //发送请求请求数据:

    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
