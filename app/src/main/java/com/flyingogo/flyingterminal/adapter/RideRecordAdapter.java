package com.flyingogo.flyingterminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flyingogo.flyingterminal.R;

import java.util.List;

/**
 * 作者：dfy on 7/7/2017 10:43
 * <p> 骑行记录adapter
 * 邮箱：dengfuyao@163.com
 */

public class RideRecordAdapter extends BaseAdapter {
    private Context          context;
    private  List<RideRecord> list;

    public RideRecordAdapter(Context context, List<RideRecord> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return 18;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ride_record, parent, false);
            holder = new ViewHolder();
            holder.tv_ride_time = (TextView) convertView.findViewById(R.id.tv_ride_time);
            holder.tv_riderecord_star = (TextView) convertView.findViewById(R.id.tv_riderecord_star);
            holder.tv_riderecord_end = (TextView) convertView.findViewById(R.id.tv_riderecord_end);
            holder.tv_riderecord_starNum = (TextView) convertView.findViewById(R.id.tv_riderecord_starNum);
            holder.tv_riderecord_endNum = (TextView) convertView.findViewById(R.id.tv_riderecord_endNum);
            holder.tv_riderecord_time = (TextView) convertView.findViewById(R.id.tv_riderecord_time);
            holder.tv_riderecord_money = (TextView) convertView.findViewById(R.id.tv_riderecord_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.tv_ride_time.setText(list.get(position).getTv_ride_time());

        return convertView;
    }

    class ViewHolder {

        private TextView tv_ride_time, tv_riderecord_star, tv_riderecord_end,
                tv_riderecord_starNum, tv_riderecord_endNum, tv_riderecord_time, tv_riderecord_money;

    }

}
