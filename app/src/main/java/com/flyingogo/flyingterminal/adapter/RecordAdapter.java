package com.flyingogo.flyingterminal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.flyingogo.flyingterminal.module.RecordBean;

import java.util.List;

/**
 * 作者：dfy on 8/7/2017 17:54
 * <p>  骑行记录的adapter;
 * 邮箱：dengfuyao@163.com
 */
public class RecordAdapter extends RecyclerView.Adapter {
   private Context                                 mContext;
    private List<RecordBean.DataBean.DataListBean> mListBeen;

   @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
