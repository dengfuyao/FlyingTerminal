package com.flyingogo.flyingterminal.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.contants.Contants;

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
    @BindView(R.id.bt_query)
    Button         mQuery;

    @Override
    protected int getResLayoutID() {
        return R.layout.abnormal_activity;
    }

    @Override
    protected void onInit() {
        mBack.setImageResource(R.drawable.activity_title_abnormal);

    }

    @OnClick({R.id.bt_query,R.id.right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right:
                finish();
                break;

            case R.id.bt_query:
                String abnormal_card_no = mEtAbnnormalCard.getText().toString().trim();
                if (TextUtils.isEmpty(abnormal_card_no)){
                    Toast.makeText(mContext, "对不起,您输入的卡号有误!", Toast.LENGTH_SHORT).show();
                    return;
                }
                toActivity(CardManageActivity.class, Contants.CARD_NO,abnormal_card_no);  //跳转到卡管理页面,附带卡号
                break;
            default:
                break;
        }
    }
}
