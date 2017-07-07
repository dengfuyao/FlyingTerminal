package com.flyingogo.flyingterminal.activity;

import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.weiget.CardInfoDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：dfy on 5/7/2017 09:25
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class BalanceCardActivity extends BaseActivity {
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

    @Override
    protected int getResLayoutID() {
        return R.layout.balance_card_activity;
    }

    @OnClick({R.id.right, R.id.iv_cardinfo_ramaining, R.id.iv_cardinfo_rading})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:   //返回按钮
                finish();
                break;
           case R.id.iv_cardinfo_ramaining:   //余额查询
               Toast.makeText(mContext, "查询余额", Toast.LENGTH_SHORT).show();
               showCardInfoDialog();
                break;
            case R.id.iv_cardinfo_rading:      //骑行记录
                Toast.makeText(mContext, "骑行记录", Toast.LENGTH_SHORT).show();
                toActivity(RideRecordActivity.class);  //跳转到查询页面
                break;
        }
    }

    private void showCardInfoDialog() {
        CardInfoDialog dialog = new CardInfoDialog(BalanceCardActivity.this);
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
}
