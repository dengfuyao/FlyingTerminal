package com.flyingogo.flyingterminal.activity;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.flyingogo.flyingterminal.application.Myplication.verifyStoragePermissions;

public class StationInforMationActivity extends BaseActivity implements AMapLocationListener, AMap.OnMapLoadedListener, AMap.OnMyLocationChangeListener {

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
    private Marker           mLocationMarker,movingMark;
    private LatLng           mLatLng;
    private BitmapDescriptor chooseDescripter ,pileDescripter ,screenDescropter;
    boolean isDestroy = false;
    private ValueAnimator animator = null;//坐标动画

    private boolean isClickIdentification = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private AMapLocation    mLocation;
    private double mLocationLatitude;
    private double mLocationLongitude;


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
        mBack.setImageResource(R.drawable.activity_title_site);  //标题

        initMap();
        setMultiPoint(); //设置海量点;
      //  createMovingPosition();

        getUpLocationStyle();
    }



    /**
     * 设置海量图
     */
    private void setMultiPoint() {

        //添加一个Marker用来展示海量点点击效果
        final Marker marker = aMap.addMarker(new MarkerOptions()
                .icon(pileDescripter));

        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        overlayOptions.icon(pileDescripter);
        overlayOptions.anchor(.5f,.5f);
        final MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);
        aMap.setOnMultiPointClickListener(new AMap.OnMultiPointClickListener() {
            @Override
            public boolean onPointClick(MultiPointItem pointItem) {
                android.util.Log.i("amap ", "onPointClick");

                //添加一个Marker用来展示海量点点击效果
                marker.setPosition(pointItem.getLatLng());
                marker.setToTop();
                return false;
            }
        });

        aMap.moveCamera(CameraUpdateFactory.zoomTo(3));
        //开启子线程获取文件中保存的经纬度
        //// TODO: 6/7/2017  拿到站点的经纬度,以实际数据为准
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<MultiPointItem> list = new ArrayList<MultiPointItem>();
                String styleName = "style_json.json";
                FileOutputStream outputStream = null;
                InputStream inputStream = null;
                String filePath = null;
                try {
                    inputStream = getResources().openRawResource(R.raw.point10w);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while((line = bufferedReader.readLine()) != null) {

                        if(isDestroy) {
                            //已经销毁地图了，退出循环
                            return;
                        }

                        String[] str = line.split(",");
                        if(str == null) {
                            continue;
                        }
                        double lat = Double.parseDouble(str[1].trim());
                        double lon = Double.parseDouble(str[0].trim());

                        LatLng latLng = new LatLng(lat, lon, false);//保证经纬度没有问题的时候可以填false

                        MultiPointItem multiPointItem = new MultiPointItem(latLng);
                        list.add(multiPointItem);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();

                        if (outputStream != null)
                            outputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(multiPointOverlay != null) {
                    multiPointOverlay.setItems(list);
                    multiPointOverlay.setEnable(true);
                }


            }
        }).start();
        List<MultiPointItem> list = new ArrayList<MultiPointItem>();
      /*  while(...) {
            ...*/
            //创建MultiPointItem存放，海量点中某单个点的位置及其他信息
            MultiPointItem multiPointItem = new MultiPointItem(mLatLng);
            list.add(multiPointItem);
       // }
        multiPointOverlay.setItems(list);//将规范化的点集交给海量点管理对象设置，待加载完毕即可看到海量点信息

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
        if (aMap == null)
            aMap = mMapView.getMap();
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMyLocationChangeListener(this);
        //aMap.setOnMapTouchListener(this);  //地图触摸事件
        //大头针;
        chooseDescripter = BitmapDescriptorFactory.fromResource(R.drawable.icon_loaction_start);
        screenDescropter = BitmapDescriptorFactory.fromResource(R.drawable.icon_loaction_choose);
        //海量图;
        pileDescripter = BitmapDescriptorFactory.fromResource(R.drawable.stable_cluster_marker_mound_normal);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        //设置定位回调监听
        mLocationClient.setLocationListener(this);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        isDestroy = true;
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        //添加Marker显示定位位置
        if (movingMark!=null){
            movingMark.remove();
        }

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

    boolean isFirstLoc = true;

    //监听定位的回调,获取到最新的信息;
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        Log.e(TAG, "onLocationChanged: 执行定位时回调" );
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                if (mLocation!=null&&aMapLocation!=null){
                    if (mLocation.getLatitude()==aMapLocation.getLatitude()
                            &&mLocation.getLongitude()==aMapLocation.getLongitude())
                        return;;
                }
                mLocation = aMapLocation;
                //可在其中解析amapLocation获取相应内容。
                //取出经纬度
                mLatLng = new LatLng(aMapLocation.getLatitude(),
                        aMapLocation.getLongitude());

                //添加小蓝点,显示地图位置;
/*
                if (isFirstLoc) {  //设置标志位,让定位只执行一次,否则在地图缩放的时候会自动回复到当前点
                    //然后可以移动到定位点,使用animateCamera就有动画效果
                    isFirstLoc = false;
                    Log.e(TAG, "onLocationChanged: 进来一次..." );
                   // getUpLocationStyle();
                   aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 10));//参数提示:1.经纬度 2.缩放级别

                }*/
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }

        Log.e(TAG, "onLocationChanged: 重新定位了一次");

    }

    //地图加载完成调用一次;
    @Override
    public void onMapLoaded() {
        Log.e(TAG, "onMapLoaded: 这个方法调用了" );
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions.snippet("最快1分钟到达").draggable(true).setFlat(true);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),                                R.drawable.icon_loaction_start)));
        movingMark = aMap.addMarker(markerOptions);
        movingMark.showInfoWindow();//主动显示indowindow
        movingMark.setPositionByPixels(mMapView.getWidth() / 2,mMapView.getHeight() / 2);
        //mLocationTask.startSingleLocate();
    }

    //开启单次定位
    public void startSingleLocate() {
        AMapLocationClientOption option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    @Override
    public void onMyLocationChange(Location location) {

        Log.e(TAG, "onMyLocationChange: 连序定位时候调用" );
        if (location != null) {

                mLocationLatitude = location.getLatitude();
                mLocationLongitude = location.getLongitude();

                if (isFirstLoc) {
                    if (mLocationLatitude > 0 && mLocationLongitude > 0) {
                        Log.e(TAG, "onMyLocationChange: 定位到新的位置" );
                        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom
                                (new LatLng(mLocationLatitude, mLocationLongitude), 13);
                        aMap.moveCamera(cu);
                    } else {
                        Log.e(TAG, "onMyLocationChange: 定位在初始化的位置" );
                        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom
                                (new LatLng(mLocation.getLatitude(), mLocation.getLongitude()),13);
                        aMap.moveCamera(cu);
                    }
                    isFirstLoc = false;
                }
            } else {
                Toast.makeText(mContext, "定位失败请检查权限", Toast.LENGTH_SHORT).show();
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
                mLocationClient.startLocation();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

}

