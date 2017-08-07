package com.flyingogo.flyingterminal.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.flyingogo.flyingterminal.R;
import com.flyingogo.flyingterminal.base.BaseActivity;
import com.flyingogo.flyingterminal.dialog.LoadDialog;
import com.flyingogo.flyingterminal.model.Stationbean;
import com.flyingogo.flyingterminal.utils.AMapUtil;
import com.flyingogo.flyingterminal.utils.NavigationBarHelp;
import com.flyingogo.flyingterminal.utils.ToastUtil;
import com.flyingogo.flyingterminal.utils.URLUtils;
import com.flyingogo.flyingterminal.utils.WalkRouteOverlay;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.flyingogo.flyingterminal.application.Myplication.verifyStoragePermissions;

/**
 * 站点查询:通过当前位置来查询附近的站点信息显示在地图中;
 * 站点查询接口:
 * http://14.154.31.54:8081/bus/bike/queryBikeStationByCoord.do?longitude=116.426905&latitude=25.053924&distance=1&lonDistance=1
 */

public class StationInforMationActivity extends BaseActivity implements AMapLocationListener, LocationSource, AMap.OnCameraChangeListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener, AMap.OnMapClickListener {

    private static final String TAG             = "StationInforMationActivity";
    private final        int    ROUTE_TYPE_WALK = 3;
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
    private Marker mLocationMarker, movingMark, tempMark;
    private BitmapDescriptor moveIcon, stationIconGreen, stationIconViolet, stationIconRed, stationIconBlack;
    boolean isDestroy = false;
    private ValueAnimator animator = null;//坐标动画

    private boolean isClickIdentification = false;  //marker被点击调用;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient                          mClient;
    private AMapLocation                             mLocation;


    private LocationSource.OnLocationChangedListener mListener;
    private UiSettings                               mUiSettings;
    private final Map<String, Marker> mBikeMarkers = new HashMap<>();
    private int mSize;
    ValueAnimator mAnimator = null; //marker动画;
    private LatLng latLng, mRecordPositon;
    private WalkRouteOverlay walkRouteOverlay;
    private LatLonPoint      mStartPoint;
    private LatLonPoint      mEndPoint;

    private RouteSearch      mRouteSearch;
    private WalkRouteResult  mWalkRouteResult;
    private BitmapDescriptor mClickIcon;
    private boolean isFist = true;
    private String  mWhenabouts;
    private int     mWidth;
    private int     mHeight;
    private Map<MarkerOptions,Stationbean.DataBean > markerOptionsListInView = new HashMap<>();

    @Override
    protected int getResLayoutID() {
        return R.layout.activity_location;
    }

    @Override
    protected void onInitMap(Bundle savedInstanceState) {
       mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        Log.e(TAG, "onInitMap: 创建地图" );
        if (Build.VERSION.SDK_INT >= 23) {
            verifyStoragePermissions(this);
        }
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mBack.setImageResource(R.drawable.title_bar);  //标题
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        initMap();

        initMarkerMap();  //初始化图片
        loadMapDown();
        getUpLocationStyle();
    }

    private void loadMapDown() {
        //OfflineMapManager amapManager = new OfflineMapManager();
    }

    private void getUpLocationStyle() {

        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        //  myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        //  myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        myLocationStyle.anchor(0.5f, 1f)  //设置锚点;
      /*  .strokeColor()//设置蓝点精度圆圈的边框颜色;
       .radiusFillColor()*/
                .strokeWidth(2); //设置圈的宽度边框;
        //myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);

    }

    //声明定位回调监听器

    private void initMap() {
        NavigationBarHelp.hideNavigation(mMapView);
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
            aMap.showIndoorMap(true);  //开启室内定位;
            mRouteSearch = new RouteSearch(this);
            mRouteSearch.setRouteSearchListener(this);
            //设置缩放级别

            mUiSettings = aMap.getUiSettings();
            mUiSettings.setMyLocationButtonEnabled(true);  //显示默认的定位图标
            mUiSettings.setScaleControlsEnabled(true);  //显示默认的比例尺
            mUiSettings.setZoomInByScreenCenter(true);
            mUiSettings.setZoomPosition(10);  //设置缩放按钮的位置

            aMap.setOnCameraChangeListener(this);  //地图移动监听
            aMap.setOnMarkerClickListener(this);  //marker点击监听
            aMap.setOnMapClickListener(this);
            aMap.setInfoWindowAdapter(this);
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);  //设置定个位的类型为定位模式,有定位,跟随,根据地面方向旋转
            aMap.setMyLocationEnabled(true);  //触发定位,并显示定位层;
            aMap.setLocationSource(this);//设置定位监听;
        }
        //   CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(17f);

    }

    private void initMarkerMap() {
        moveIcon = BitmapDescriptorFactory.fromResource(R.drawable.icon_loaction_start);
        stationIconViolet = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_violet);
        stationIconRed = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_red);
        stationIconBlack = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_black);
        stationIconGreen = BitmapDescriptorFactory.fromResource(R.drawable.station_icon_green);
    }



    //监听定位的回调,获取到最新的信息;
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(22.813191, 108.401286), 20f);
        aMap.moveCamera(mCameraUpdate);


        Log.e(TAG, "onLocationChanged: 位置改变的回调");
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                // 108.401286;//target.longitude;//
                //  aMap.moveCamera(CameraUpdateFactory.zoomTo(17f));
                // mLatLng = new LatLng(22.813191, 108.401286);longitude=108.38435009121898&latitude=22.813105497411687
                latLng =  new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());//new LatLng(22.813105497411687, 108.38435009121898);
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    private boolean isFirstLoad = true;

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.e(TAG, "onCameraChangeFinish: 地图移动结束");

        if (movingMark==null) {
            createMovingPosition(latLng);
        }

        if (!isClickIdentification) {   //没有点击marker
            mRecordPositon = cameraPosition.target;
        }
        LatLng target = mRecordPositon;
        // mRecordPositon = target;
        final double longitude = target.longitude;//108.401286;//
        final double latitude = target.latitude;//22.813191;//

        if (isFirstLoad) {
            addMarker(longitude, latitude);  //添加站点的标记;
            isFirstLoad = false;
            SystemClock.sleep(300);
        }
        showMarker(listData);  //显示站点信息;
        if (movingMark != null) {
            Log.e(TAG, "onCameraChangeFinish: moveingmarke移动到顶部");
            movingMark.setToTop();
            if (!isClickIdentification) {  //没有点击marker
                animMarker();

            }
        }
    }

    /**
     * 创建移动位置图标
     *
     * @param latLng
     */
    private void createMovingPosition(LatLng latLng) {
        Log.e(TAG, "createMovingPosition: 创建移动图标");
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.setFlat(true);
//        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(latLng);
        markerOptions.icon(moveIcon);
        movingMark = aMap.addMarker(markerOptions);
        //固定标志位在屏幕中心
        movingMark.setPositionByPixels(mMapView.getWidth() / 2,
                mMapView.getHeight() / 2);
        movingMark.setAnchor(.5f, 1.5f);
        movingMark.setToTop();
        movingMark.setClickable(false);
    }

    @OnClick({R.id.right, R.id.bt_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right:
                finish();
                break;
            case R.id.bt_location:
                //定位到南宁;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(22.813105497411687, 108.38435009121898), 19f));
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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
        super.onStart();
    }

    @Override
    public void onStop() {


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        isDestroy = true;
        if (mBikeMarkers.size() > 0) {
            removeMarkers();
        }

        bitMapRecycle(moveIcon, stationIconGreen, stationIconViolet, stationIconRed, stationIconBlack);
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        Log.e(TAG, "onDestroy: 地图销毁" );
        mMapView.onDestroy();
        super.onDestroy();
    }

    private void bitMapRecycle(BitmapDescriptor... bitmap) {
        for (BitmapDescriptor bit : bitmap) {
            bit.recycle();
        }
    }

    public void removeMarkers() {
        for (Marker marker : mBikeMarkers.values()) {
            marker.remove();
            marker.destroy();
        }
        mBikeMarkers.clear();
    }

    @Override
    protected void onResume() {

        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        //添加Marker显示定位位置
        //设置海量点;
        Log.e(TAG, "onResume: 重新开始");
        mMapView.onResume();

        if (movingMark != null) {
            // movingMark.remove();
            movingMark.setToTop();
        }else {
            createMovingPosition(latLng);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        movingMark.remove();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);

    }

    /**
     * aMap.setLocationSource(this)的开始定位的回调函数;
     * OnLocationChangedListener
     *
     * @param listener
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        Log.e(TAG, "activate: 急活定位");
        mListener = listener;
        if (mLocationClient == null) {
            //初始化定位;
            mLocationClient = new AMapLocationClient(this);
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
            mLocationOption.setOnceLocation(true);
            //使用缓存机制
            mLocationOption.setLocationCacheEnable(true);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }


    }

    //aMap.setLocationSource(this) 停止定位的相关回调
    @Override
    public void deactivate() {
        Log.e(TAG, "deactivate: 停止定位回调");

        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    //地图移动的回调;
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        //Log.e(TAG, "onCameraChange: 地图移动,");

    }
//通过请求得到所有站点的位置放到一个bean对象存储,放到一个集合中,遍历集合将所有站点取出创建marker;

    private final Map<String, Stationbean.DataBean> listData   = new HashMap<>();
    private void addMarker(double longitude, double latitude) {
        // mMapView.getWidth()+mMapView.getHeight()+
        listData.clear();
        String url = URLUtils.getStationUrl(longitude + "", latitude + "", "1000", "1000");
        Log.e(TAG, "addMarker: url = " + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: 获取数据失败]" + e.getMessage());
            }
            @Override
            public void onResponse(String response, int id) {
                isFirstLoad = false;
                Log.e(TAG, "onResponse: response = " + response.toString());
                Gson gson = new Gson();
                Stationbean datas = gson.fromJson(response.toString(), Stationbean.class);
                final List<Stationbean.DataBean> data = datas.data;
                Log.e(TAG, "onResponse: data = " + data.size());
                for (Stationbean.DataBean bean : data) {
                    if (isDestroy) {
                        //已经销毁地图了，直接结束;
                        return;
                    }
                    //将返回的标记添加到一个集合,相同的不添加,只添加不同的标记
                    if (!(listData.containsKey(bean.stationNo))) {
                        listData.put(bean.stationNo, bean);
                    }
                }
                Log.e(TAG,   " 返回的总站点数==" + listData.size());
            }
        });
    }

    /**
     * 根据屏幕的宽度显示站点位置图标;
     * @param listData
     */
    private void showMarker(Map<String, Stationbean.DataBean> listData) {
        mBikeMarkers.clear();
        Collection<Stationbean.DataBean> beans = listData.values();
        for (Stationbean.DataBean bean: beans ){

            LatLng latLng = new LatLng(bean.lat, bean.lon, false);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title("").anchor(0.5f, 0.92f)
                    .position(latLng).zIndex(1).icon(stationIconGreen)
                    .draggable(false);
            Projection projection = aMap.getProjection();
            Point point = projection.toScreenLocation(markerOptions.getPosition());
            if (point.x < 0 || point.y < 0 || point.x > mWidth || point.y > mHeight) {
                // 不添加到计算的列表中
            } else {
                //添加到视野中
                Marker marker = aMap.addMarker(markerOptions);
                if ((double) bean.returnCount / (double) bean.stationCount > 0.8) {
                    marker.setIcon(stationIconViolet);
                } else if ((double) bean.returnCount / (double) bean.stationCount < 0.2) {
                    marker.setIcon(stationIconRed);
                } else if (bean.returnCount == 0 && bean.borrowCount == 0) {
                    marker.setIcon(stationIconBlack);
                }
                marker.setObject(bean);
                mBikeMarkers.put(bean.stationNo, marker);
            }

        }


       /**/
    }

    //添加动画;
    private void animMarker() {
        if (animator != null) {
            animator.start();
            return;
        }
        animator = ValueAnimator.ofFloat(mMapView.getHeight() / 2, mMapView.getHeight() / 2 - 30);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(100);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                movingMark.setPositionByPixels(mMapView.getWidth() / 2, Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                movingMark.setIcon(moveIcon);
                movingMark.setToTop();
            }
        });
        animator.start();

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //market点击时候调用;
     /*   movingMark.setToTop();

        mCurShowWindowMarker = marker;
        return false;*/
// TODO: 31/7/2017 更改了调用点击弹窗的位置,;
        marker.showInfoWindow();
        marker.setBelowMaskLayer(true);
        ArrayList<BitmapDescriptor> icons = marker.getIcons();
        if (icons.size() > 0) {
            mClickIcon = icons.get(0);
            Log.e(TAG, "marker.getIcons(): " + icons.size());
            Log.e(TAG, "点击的Marker");
            Log.e(TAG, marker.getPosition() + "");
            isClickIdentification = true;

            if (tempMark != null) {
                tempMark.setIcon(mClickIcon);
                walkRouteOverlay.removeFromMap();
                tempMark = null;
            }
            startAnim(marker);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        tempMark = marker;
                        Log.e(TAG, movingMark.getPosition().latitude + "===" + movingMark.getPosition().longitude);
                        mStartPoint = new LatLonPoint(mRecordPositon.latitude, mRecordPositon.longitude);
                        movingMark.setPosition(mRecordPositon);
                        mEndPoint = new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude);
                        marker.setIcon(mClickIcon);
                        marker.setPosition(marker.getPosition());
                        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                        setZoom(19f);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.e(TAG, "onMapClick: 地图点击被调用");
        clickMap();
    }

    private void clickMap() {
        clickInitInfo();
        if (mRecordPositon != null) {
            setZoom(19f);
        }
    }

    /**
     * 设置缩放级别;
     */
    private void setZoom(float zoom) {
        CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                mRecordPositon, zoom);
        aMap.animateCamera(cameraUpate);

    }

    /**
     *
     */
    private void clickInitInfo() {
        isClickIdentification = false;//点击地图时调用这个方法;
        if (null != tempMark) {
            tempMark.setIcon(mClickIcon);
            tempMark.hideInfoWindow();
            tempMark = null;
        }
        if (null != walkRouteOverlay) {
            walkRouteOverlay.removeFromMap();
        }
    }

    private void startAnim(Marker marker) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f);
        anim.setDuration(300);
        marker.setAnimation(anim);
        marker.startAnimation();
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            ToastUtil.show(this, "定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            ToastUtil.show(this, "终点未设置");
        }
        showDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    private void showDialog() {
        LoadDialog loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        LoadDialog.getInstance().show(getSupportFragmentManager(), "");
    }

    @Override
    public View getInfoWindow(Marker marker) {
        NavigationBarHelp.hideNavigation(this);
        Log.e(TAG, "getInfoWindow: isfist++=" + isClickIdentification);
        return getInfoWindowView(marker);
    }

    private View getInfoWindowView(Marker marker) {
        View infoWindow = null;
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(this).inflate(
                    R.layout.custom_info_window, null);
            render(marker, infoWindow);
        }

        infoWindow.clearFocus();
        return infoWindow;
    }

    public void render(Marker marker, View view) {

        TextView name = (TextView) view.findViewById(R.id.station_name);
        TextView address = (TextView) view.findViewById(R.id.station_address);
        TextView usable_bike = (TextView) view.findViewById(R.id.station_usable_bike);
        TextView vacancyBike = (TextView) view.findViewById(R.id.station_vacancy_bike);
        TextView abnormalBike = (TextView) view.findViewById(R.id.station_abnormal_bike);
        TextView whenabluts = (TextView) view.findViewById(R.id.station_mWhenabouts);
        Stationbean.DataBean bean = (Stationbean.DataBean) marker.getObject();
        name.setText(bean.stationName);
        address.setText(bean.stationAddress);
        usable_bike.setText(bean.borrowCount + "");
        vacancyBike.setText(bean.returnCount + "");
        abnormalBike.setText(bean.exceptionCount + "");
        whenabluts.setText(mWhenabouts);
        Log.e(TAG, "render: " + mWhenabouts);
    }
    @Override
    public View getInfoContents(Marker marker) {
        Log.e(TAG, "getInfoContents");
        return null;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        LoadDialog.getInstance().dismiss();
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();  //添加路线;
                    walkRouteOverlay.zoomToSpan();
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    Log.e(TAG, "onWalkRouteSearched: dis = " + dis + "  dur= " + dur);
                    mWhenabouts = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    Log.e(TAG, "onWalkRouteSearched: 开始调用720");

                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(this, R.string.no_result);
                }
            } else {
                ToastUtil.show(this, R.string.no_result);
            }
        } else {
            ToastUtil.show(mContext, errorCode);
        }
    }
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

}

