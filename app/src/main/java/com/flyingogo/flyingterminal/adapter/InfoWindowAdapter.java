package com.flyingogo.flyingterminal.adapter;

/**
 * 作者：dfy on 20/7/2017 16:05
 * <p> 自定义的market弹窗;
 * 邮箱：dengfuyao@163.com
 */
   /*public class InfoWindowAdapter implements AMap.InfoWindowAdapter {

 private Context mContext;
    private Marker  marker;

    public InfoWindowAdapter(Context context, Marker marker) {
        mContext = context;
        this.marker = marker;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return getInfoWindowView();
    }

    View infoWindow = null;
    private static final String TAG = "InfoWindowAdapter";

    private View getInfoWindowView() {
        Log.e(TAG, "getInfoWindowView: 111111111111");
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(mContext).inflate(
                    R.layout.custom_info_window, null);

        }
        render(marker, infoWindow);
        return infoWindow;

    }

    public void render(Marker marker, View view) {
        TextView name = (TextView) view.findViewById(R.id.station_name);
        TextView address = (TextView) view.findViewById(R.id.station_address);
        TextView usable_bike = (TextView) view.findViewById(R.id.station_usable_bike);
        TextView vacancyBike = (TextView) view.findViewById(R.id.station_vacancy_bike);
        TextView abnormalBike = (TextView) view.findViewById(R.id.station_abnormal_bike);

        Stationbean.DataBean bean = (Stationbean.DataBean) marker.getObject();
       name.setText(bean.stationName);
        address.setText(bean.stationAddress);
        usable_bike.setText(bean.borrowCount+"");
        vacancyBike.setText(bean.returnCount+"");
        abnormalBike.setText(bean.exceptionCount+"");
    }


    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
*/