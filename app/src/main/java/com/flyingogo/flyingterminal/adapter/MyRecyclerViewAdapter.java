package com.flyingogo.flyingterminal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.flyingogo.flyingterminal.model.RecordBean;
import com.flyingogo.flyingterminal.weiget.RecordsView;

import java.util.List;

/**
 * 作者：dfy on 4/7/2017 09:58
 * <p>
 * 邮箱：dengfuyao@163.com
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context
                                                   mContext;
    private List<RecordBean.DataBean.DataListBean> mDatas;

    public  MyRecyclerViewAdapter(Context context,List<RecordBean.DataBean.DataListBean> dataListBeen) {
        mContext = context;
        mDatas = dataListBeen;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new RecordsView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ((RecordsView)viewHolder.itemView).bindView(mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
