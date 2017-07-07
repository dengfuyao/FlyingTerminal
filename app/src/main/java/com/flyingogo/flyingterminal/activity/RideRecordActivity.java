package com.flyingogo.flyingterminal.activity;

import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.bean.Rideean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
        //todo 发送请求请求数据:

        String url = "https://github.com/hongyangAndroid/okhttputils";
        OkHttpUtils
                .get()
                .url(url)
              /*  .addParams("username", "hyman")
                .addParams("password", "123")*/
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("请求", "onResponse: response = " + response);
                        Gson gson = new Gson();
                        gson.fromJson(response, Rideean.class);
                    }

                });
    }

    @OnClick(R.id.right)
    public void onClick() {
        finish();
    }
}
