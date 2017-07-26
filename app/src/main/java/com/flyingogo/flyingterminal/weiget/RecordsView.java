package com.flyingogo.flyingterminal.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.module.RecordBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：dfy on 4/7/2017 10:13
 * <p>  骑行记录的view
 * 邮箱：dengfuyao@163.com
 */

public class RecordsView extends LinearLayout {

    @BindView(R.id.tv_ride_time)
    TextView mTvRideTime;
    @BindView(R.id.tv_riderecord_star)  //借车地区
    TextView mTvRiderecordStar;
    @BindView(R.id.tv_riderecord_end)   //还车地区
    TextView mTvRiderecordEnd;
    @BindView(R.id.tv_riderecord_starNum)  //借车的锁住编号
    TextView mTvRiderecordStarNum;
    @BindView(R.id.tv_riderecord_endNum)  //还车的锁柱编号
    TextView mTvRiderecordEndNum;
    @BindView(R.id.tv_riderecord_time)   //使用时间
    TextView mTvRiderecordTime;
    @BindView(R.id.tv_riderecord_money)   //消费金额
    TextView mTvRiderecordMoney;

    public RecordsView(Context context) {
        this(context, null);
    }

    public RecordsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_layout, this, true);
        ButterKnife.bind(this, this);
    }

    public void bindView(RecordBean.DataBean.DataListBean data, int postion) {

       mTvRideTime.setText(data.borrowTime1);
        mTvRiderecordStar.setText(data.borrow_Location);
        mTvRiderecordEnd.setText(data.return_Location);
        mTvRiderecordTime.setText(data.ridingMinute+"");  //使用时间
        mTvRiderecordMoney.setText(data.cost+"");
        mTvRiderecordStarNum.setText(data.borrowStation);  //借车站点编号
        mTvRiderecordEndNum.setText(data.returnStation);  //还车站点编号



    }
}
