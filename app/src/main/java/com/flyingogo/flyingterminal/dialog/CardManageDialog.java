package com.flyingogo.flyingterminal.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.flyingogo.flyingterminal.R.id.iv_close;

/**
 * 作者：dfy on 11/7/2017 11:15
 * <p>  异常卡处理的弹框;收到服务器的返回数据就弹出框;
 * 邮箱：dengfuyao@163.com
 */

public class CardManageDialog extends Dialog {

    @BindView(R.id.manage_result)
    TextView  mManageResult;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.message)
    TextView  mMessage;
    private Context mContext;

    public CardManageDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.mContext = context;
        initView(context);
    }


    private void initView(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_cardmanage, null);
        setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this,this);
        cleanFocus();
//        bt_Sub = (Button) findViewById(R.id.cardinfo_submern);
    }

    public void setUpData(int result, String message) {

       switch (result){
           case 1:
               mManageResult.setText("处理成功!");
               break;

           default:
               mManageResult.setText("处理失败");
               break;
       }
        mMessage.setText(message);
    }

    public void noNetwork(){
        mManageResult.setText("网络异常,请重新进入");
    }

    @OnClick(iv_close)
    public void onClick() {

        dismiss();
    }
    private void cleanFocus() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        this.setCanceledOnTouchOutside(true);
    }

}
