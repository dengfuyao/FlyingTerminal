package com.flyingogo.flyingterminal.activity;

import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.TextOptions;
import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.module.Stationbean;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.flyingogo.flyingterminal.application.Myplication.verifyStoragePermissions;

/**
 * 站点查询:通过当前位置来查询附近的站点信息显示在地图中;
 * 站点查询接口:
 * http://14.154.31.54:8081/bus/bike/queryBikeStationByCoord.do?longitude=116.426905&latitude=25.053924&distance=1&lonDistance=1
 */

public class StationInforMationActivity extends BaseActivity implements AMapLocationListener, LocationSource, AMap.OnCameraChangeListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, AMap.OnMapTouchListener {

    private static final String TAG = "StationInforMationActivity";

    @BindView(R.id.mapview)
    MapView        mMapView;
    @BindView(R.id.back)
    ImageView      mBack;
    @BindView(R.id.right)
    TextView       mRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.activity_title)
    RelativeLayout mActivityTitle;
    @BindView(R.id.activity_main)
    LinearLayout   mActivityMain;
    @BindView(R.id.bt_location)
    ImageView      mBtLocation;
    private AMap aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient       mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private Marker mLocationMarker, movingMark;
    private static LatLng           mLatLng;
    private        BitmapDescriptor chooseDescripter, pileDescripter, stationIconGreen,stationIconViolet,stationIconRed,stationIconBlack;
    boolean isDestroy = false;
    private ValueAnimator animator = null;//坐标动画

    private boolean isClickIdentification = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient                          mClient;
    private AMapLocation                             mLocation;
    private double                                   mLocationLatitude;
    private double                                   mLocationLongitude;
    private LocationSource.OnLocationChangedListener mListener;
    private UiSettings                               mUiSettings;
    private HashSet<Marker>                          mBikeMarkers  = new LinkedHashSet<>();
    private Marker mCurShowWindowMarker;


    @Override
    protected int getResLayoutID() {
        return R.layout.activity_location;
    }

    @Override
    protected void onInitMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            verifyStoragePermissions(this);
        }
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mBack.setImageResource(R.drawable.title_bar);  //标题

        initMap();

        //  createMovingPosition();

        getUpLocationStyle();
    }

    private void getUpLocationStyle() {

        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        //  myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        myLocationStyle.anchor(0.5f, 1f)  //设置锚点;
      /*  .strokeColor()//设置蓝点精度圆圈的边框颜色;
       .radiusFillColor()*/
                .strokeWidth(2); //设置圈的宽度边框;
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);

    }

    //声明定位回调监听器

    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.showIndoorMap(true);  //开启室内定位;
        }
       // aMap.setInfoWindowAdapter( new InfoWindowAdapter(mContext));//设置自定义market弹窗;
        chooseDescripter = BitmapDescriptorFactory.fromResource(R.drawable.icon_loaction_start);
        stationIconViolet = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_violet);
        stationIconRed= BitmapDescriptorFactory.fromResource(R.drawable.station_icon_red);
        stationIconBlack = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_black);
        stationIconGreen = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_green);
        //stationIconGreen.recycle();
        //海量图;
        pileDescripter = BitmapDescriptorFactory.fromResource(R.drawable.stable_cluster_marker_mound_normal);
        aMap.setLocationSource(this);//设置定位监听;
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);  //显示默认的定位图标
        mUiSettings.setScaleControlsEnabled(true);  //显示默认的比例尺
        mUiSettings.setZoomInByScreenCenter(true);
        //   mUiSettings.setZoomPosition(10);  //设置缩放按钮的位置
        aMap.setMyLocationEnabled(true);  //触发定位
        aMap.setOnCameraChangeListener(this);  //地图移动监听

        aMap.setOnMarkerClickListener(StationInforMationActivity.this);  //marker点击监听
        aMap.setInfoWindowAdapter(StationInforMationActivity.this);
        aMap.setOnMapTouchListener(this);

    }
    boolean isFirstLoc = true;

    //监听定位的回调,获取到最新的信息;
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
               // 108.401286;//target.longitude;//

                mLatLng =   new LatLng(22.813191,108.401286);
               // mLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                if (movingMark == null) { //如果是空的添加一个新的,icon方法就是设置定位图标，可以自定义
                    movingMark = aMap.addMarker(new MarkerOptions()
                            .position(mLatLng)
                            .anchor(.5f, 1.5f)
                            .icon(chooseDescripter)
                            .draggable(true).setFlat(false));
                    movingMark.showInfoWindow();//主动显示indowindow
                    aMap.addText(new TextOptions()
                            .position(mLatLng)
                            .text(aMapLocation
                                    .getAddress())); //固定标签在屏幕中央
                    movingMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
                    CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(13);  //设置缩放级别,:注意,必须要在定位以后才起作用,
                    aMap.moveCamera(mCameraUpdate);
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    movingMark.setPosition(mLatLng);
                    movingMark.setClickable(false);

                } else { //已经添加过了，修改位置即可
                    movingMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
                    // MarkerOptions options = movingMark.getOptions();
                }
                // movingMark.setPosition(mLatLng); }

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }

        }

    }

    @OnClick({R.id.right, R.id.bt_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                finish();
                break;
            case R.id.bt_location:
                //从新定位;
                isFirstLoc = true;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 13));
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("StationInforMation Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        isDestroy = true;
        mMapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {

        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        //添加Marker显示定位位置
        //设置海量点;
        Log.e(TAG, "onResume: 重新开始" );
        mMapView.onResume();

//        aMap.notify();
        if (movingMark != null) {
           // movingMark.remove();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());

            //设置定位回调监听
            mLocationClient.setLocationListener(StationInforMationActivity.this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。通过GPS和网络同时定位
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否返回地址信息（默认返回地址信息）
            // mLocationOption.setNeedAddress(true);
            //设置是否允许模拟位置,默认为true，允许模拟位置
            mLocationOption.setMockEnable(true);
            //设置只定位一次;
            //  mLocationOption.setOnceLocation(true);
            //使用缓存机制
            mLocationOption.setLocationCacheEnable(true);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }


    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.e(TAG, "onCameraChange: 地图移动,");
        // TODO: 13/7/2017 加载出站点的位置信息;
        // setMultiPoint();


    }


    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.e(TAG, "onCameraChangeFinish: 地图移动结束");
/*
        final Marker marker = aMap.addMarker(new MarkerOptions()
                .icon(pileDescripter));*/

        LatLng target = cameraPosition.target;
        final double longitude = target.longitude;//108.401286;//
        final double latitude = target.latitude;//22.813191;//
        addMarket(longitude, latitude);  //添加站点的标记;

    }

    private void addMarket(double longitude, double latitude) {
       // mMapView.getWidth()+mMapView.getHeight()+
        String url = URLUtils.getStationUrl(longitude + "", latitude + "", "1000", "2");
        Log.e(TAG, "addMarket: url = "+url );
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: 获取数据失败]");
            }
            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: response = " + response.toString());
                Gson gson = new Gson();
                Stationbean datas = gson.fromJson(response.toString(), Stationbean.class);
                final List<Stationbean.DataBean> data = datas.data;
                Log.e(TAG, "onResponse: data = " + data.size());
                List<MultiPointItem> list = new ArrayList<>();
                for (Stationbean.DataBean bean : data) {
                    if (isDestroy) {
                        //已经销毁地图了，直接结束;
                        return;
                    }
                    LatLng latLng = new LatLng(bean.lat, bean.lon, false);//保证经纬度没有问题的时候可以填false

                    Marker marker = aMap.addMarker(new MarkerOptions() .title("") .anchor(0.5f, 0.92f)
                            .position(latLng).zIndex(1) .icon(stationIconGreen)
                            .draggable(false));
                   if ((double)bean.returnCount/(double) bean.stationCount>0.8){
                       marker.setIcon(stationIconViolet);
                   }else if ((double)bean.returnCount/(double) bean.stationCount<0.2){
                       marker.setIcon(stationIconRed);
                   }else if (bean.returnCount==0&& bean.borrowCount==0){
                       marker.setIcon(stationIconBlack);
                   }
                    marker.setObject(bean);  //将数据传出;
                    mBikeMarkers.add(marker);

             //       marker.showInfoWindow();
                }
                Log.e(TAG, "onResponse: list.size = "+list.size() );

            }
        });
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
       //market点击时候调用;
        mCurShowWindowMarker = marker;
        return false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return getInfoWindowView(marker);
    }
    private View getInfoWindowView(Marker marker) {
        View infoWindow = null;
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(this).inflate(
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
        return getInfoWindow(marker);
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (aMap != null && mCurShowWindowMarker != null) {
            if (mCurShowWindowMarker.isInfoWindowShown()){
                mCurShowWindowMarker.hideInfoWindow();
            }
        }
    }
}

