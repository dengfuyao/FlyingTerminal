package com.flyingogo.flyingterminal.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;
import com.flyingogo.flyingterminal.model.ExceptionBean;
import com.flyingogo.flyingterminal.model.RecordBean;
import com.flyingogo.flyingterminal.utils.ThreadUtils;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.flyingogo.flyingterminal.dialog.CardManageDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 作者：dfy on 11/7/2017 10:53
 * <p> 卡管理的界面(异常处理);
 * 邮箱：dengfuyao@163.com
 */
public class CardManageActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.card_staus)
    TextView       mCardStaus;
    @BindView(R.id.tv_latest_borrow_location)
    TextView       mTvLatestBorrowLocation;
    @BindView(R.id.tv_latest_return_location)
    TextView       mTvLatestReturnLocation;
    @BindView(R.id.tv_riderecord_starNum)
    TextView       mTvRiderecordStarNum;
    @BindView(R.id.tv_riderecord_endNum)
    TextView       mTvRiderecordEndNum;
    @BindView(R.id.tv_riderecord_time)
    TextView       mTvRiderecordTime;
    @BindView(R.id.tv_riderecord_money)
    TextView       mTvRiderecordMoney;
    @BindView(R.id.bikeNo)
    TextView       mBikeNo;
    @BindView(R.id.borrow_time)
    TextView       mBorrowTime;
    @BindView(R.id.bt_repairs)
    Button         mBtRepairs;
    private String mCard_no;

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_card_manage;
    }

    @Override
    protected void onInit() {
        Intent intent = getIntent();
        mCard_no = intent.getStringExtra(Contants.CARD_NO);
        loadUpData(mCard_no);


    }

    private static final String TAG = "CardManageActivity";

    private void loadUpData(String card_no) {
        //发起请求获取到卡最近的使用情况
      //  String url = Contants.URL_RECORD + card_no;

        String url = URLUtils.getRecordCardUrl("1","1",card_no);

        OkHttpUtils.get().url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: response" + response.toString());
                Gson gson = new Gson();
                RecordBean recordBean = gson.fromJson(response.toString(), RecordBean.class);
                final List<RecordBean.DataBean.DataListBean> dataList = recordBean.data.dataList;
                Log.e(TAG, "onResponse: dastalist = " + dataList.size());
                ThreadUtils.onRunUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dataList.size()>0) {
                            bindData(dataList.get(0));
                        }else{
                            bindData();
                        }
                    }
                });
            }
        });


    }

    private void bindData(){
        mBikeNo.setText(mCard_no+"");


    }
    private void bindData(RecordBean.DataBean.DataListBean bean) {
        //STATE：状态（1借车中，2已还车，3借车异常，4   还车异常）;
        //自行车编号
        mBikeNo.setText(bean.bikeNo);
        //   bean.cardNo;//卡编号
        mTvLatestBorrowLocation.setText(bean.borrow_Location);
        ;//借车地址

        mBorrowTime.setText(bean.borrowTime1);
        ;
        mTvLatestReturnLocation.setText(bean.return_Location);
        ;

        mTvRiderecordTime.setText(bean.ridingMinute + "");
        mTvRiderecordMoney.setText(bean.cost + "");

        mTvRiderecordStarNum.setText(bean.borrowLock);
        ; //借锁编号
        mTvRiderecordEndNum.setText(bean.returnLock);//还锁编号
        String state = bean.state;//当前状态;
        String status = "";
        switch (state) {
            case "1":
                status = "骑行中...";
                break;
            case "2":
                mBtRepairs.setVisibility(View.INVISIBLE);
                status = "车已还好";
                break;
            case "3":
                status = "借车异常";
                break;
            case "4":
                status = "还车异常";
                break;
            default:
                status = "开个玩笑!";
                break;
        }
        Log.e(TAG, "bindData: state = " + status);
        Log.e(TAG, "bindData: state = " + state);
        mCardStaus.setText(status + "");
    }

    @OnClick({R.id.right,R.id.bt_repairs})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right:
                finish();
                break;
            case R.id.bt_repairs:
                //点击发起请求,通知后台更改异常卡的状态;
                loadRepairs();
                break;
            default:
                break;
        }
    }

    private void loadRepairs() {
        String url = URLUtils.getExceptionCardUrl(mCard_no);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
              showCardManageDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                //Log.e(TAG, "onResponse: response"+response.toString() );
               // loadUpData(mCard_no);
                Gson gson = new Gson();
                ExceptionBean exceptionBean = gson.fromJson(response.toString(), ExceptionBean.class);
                showCardManageDialog(exceptionBean.state,exceptionBean.message);
            }
        });
    }


    private void showCardManageDialog(int state, String message) {
        CardManageDialog dialog = new CardManageDialog(this);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.70); //宽度设置为屏幕
        p.height = (int) (d.getHeight() * 0.60);
        dialog.getWindow().setAttributes(p); //设置生效
        dialog.setUpData(state,message);
        dialog.show();
    }
    private void showCardManageDialog(){
        CardManageDialog dialog = new CardManageDialog(this);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.70); //宽度设置为屏幕
        p.height = (int) (d.getHeight() * 0.60);
        dialog.getWindow().setAttributes(p); //设置生效
        dialog.noNetwork();
        dialog.show();

    }


}
