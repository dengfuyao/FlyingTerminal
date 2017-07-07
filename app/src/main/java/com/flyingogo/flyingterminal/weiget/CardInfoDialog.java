package com.flyingogo.flyingterminal.weiget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyingogo.flyingterminal.R;

/**
 * 作者：dfy on 7/7/2017 10:14
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class CardInfoDialog extends Dialog implements View.OnClickListener {
    private Button    bt_Sub;
    private ImageView iv_close;
    private Context   context;

    public CardInfoDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_cardinfo, null);
        setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
//        bt_Sub = (Button) findViewById(R.id.cardinfo_submern);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cardinfo_submern:
//                dismiss();
//                break;

            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
