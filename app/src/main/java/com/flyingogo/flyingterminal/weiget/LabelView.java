package com.flyingogo.flyingterminal.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：dfy on 4/7/2017 10:13
 * <p>
 * 邮箱：dengfuyao@163.com
 */

public class LabelView extends LinearLayout {
    @BindView(R.id.textView)
    TextView mTextView;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_layout, this, true);
        ButterKnife.bind(this,this);
    }

    public void bindView(String data,int postion) {
       mTextView.setText(data+ postion);
    }
}
