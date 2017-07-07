package com.flyingogo.flyingterminal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.flyingogo.flyingterminal.weiget.LabelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：dfy on 4/7/2017 09:58
 * <p>
 * 邮箱：dengfuyao@163.com
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context
            mContext;
    private List<String> mMyViews;

    public  MyRecyclerViewAdapter(Context context) {
        mContext = context;
        mMyViews = new ArrayList<>();
        for (int i =0 ;i<4 ;i++){
            String data = "点我";
            mMyViews.add(data);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new LabelView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ((LabelView)viewHolder.itemView).bindView(mMyViews.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mMyViews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
