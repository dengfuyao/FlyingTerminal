package com.flyingogo.flyingterminal.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.flyingogo.flyingterminal.R;

/**
 * 作者：dfy on 17/7/2017 16:15
 * <p> 支付按钮的组合控件
 * 邮箱：dengfuyao@163.com
 */

public class RechangeView extends LinearLayout {
    public RechangeView(Context context) {
        this(context,null);
    }

    public RechangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.recharge_item,this);

    }
}
