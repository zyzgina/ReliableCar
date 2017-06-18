package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;


/**
 * 地图
 * Created by Doris on 2017/5/26.
 */
public class MapActivity extends Activity {

    private RadioGroup mapTripMode;
    private RadioButton mapTripModeTaxi, mapTripModeBus, mapTripModeWalk;
    private TextView mapDestinationAddress, mapDistance;

    private MapView mMapView;
    private AMap aMap;
    private CameraUpdate cameraUpdate;
    private LatLng latLng;
    public LoadingDialog dialog;
    private LatLonPoint mStartPoint;// 起点，
    private LatLonPoint mEndPoint;// 终点，
    private boolean isFirst = true;
    private String[][] mapInfos = new String[3][2];
    private String city = "北京"; // 默认

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            // TODO Auto-generated method stub
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    double longitude = amapLocation.getLongitude();//获取经度
                    double latitude = amapLocation.getLatitude(); //获取纬度
                    LogUtils.debug("locationType:" + longitude + ",latitude:" + latitude);
                    city = amapLocation.getCity();
                    mStartPoint = new LatLonPoint(latitude, longitude);
                    pathPlanning();
                } else {
                    //定位失败，ErrCode：错误码，errInfo：错误信息
                    LogUtils.debug("location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
                }
            }
        }
    };
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    // 路径规划
    private RouteSearch routeSearch;
    private RouteSearch.OnRouteSearchListener onRouteSearchListener = new RouteSearch.OnRouteSearchListener() {
        @Override
        public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
            if (i == 1000) {
                // 成功
                // 步行距离加公交行驶距离
                float distance = busRouteResult.getPaths().get(0).getBusDistance() + busRouteResult.getPaths().get(0).getWalkDistance();
                mapInfos[1][0] = "距离" + IBaseMethod.computingDistance(distance);
                long duration = busRouteResult.getPaths().get(0).getDuration(); // 秒
                mapInfos[1][1] = DateUtil.calculateTime(duration);
            } else {
                mapInfos[1][0] = "路径规划失败";
                mapInfos[1][1] = "路径规划失败";
            }
            if (mapInfosIsAdopt()) {
                mHandler.sendEmptyMessage(IBase.CONSTANT_SEX);
            }
        }

        @Override
        public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
            if (i == 1000) {
                // 成功
                float distance = driveRouteResult.getPaths().get(0).getDistance(); // 米
                mapInfos[0][0] = "距离" + IBaseMethod.computingDistance(distance);
                long duration = driveRouteResult.getPaths().get(0).getDuration(); // 秒
                mapInfos[0][1] = DateUtil.calculateTime(duration);
            } else {
                mapInfos[0][0] = "路径规划失败";
                mapInfos[0][1] = "路径规划失败";
            }
            if (mapInfosIsAdopt()) {
                mHandler.sendEmptyMessage(IBase.CONSTANT_SEX);
            }
        }

        @Override
        public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
            if (i == 1000) {
                // 成功
                float distance = walkRouteResult.getPaths().get(0).getDistance();
                mapInfos[2][0] = "距离" + IBaseMethod.computingDistance(distance);
                long duration = walkRouteResult.getPaths().get(0).getDuration(); // 秒
                mapInfos[2][1] = DateUtil.calculateTime(duration);
            } else {
                mapInfos[2][0] = "路径规划失败";
                mapInfos[2][1] = "路径规划失败";
            }
            if (mapInfosIsAdopt()) {
                mHandler.sendEmptyMessage(IBase.CONSTANT_SEX);
            }
        }

        @Override
        public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IBase.CONSTANT_SEX:
                    dialog.dismiss();
                    mapTripModeTaxi.setText(mapInfos[0][1]);
                    mapTripModeBus.setText(mapInfos[1][1]);
                    mapTripModeWalk.setText(mapInfos[2][1]);
                    mapTripModeTaxi.setChecked(true);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));

        initView(savedInstanceState);
        initMap();
    }

    private void initView(Bundle savedInstanceState) {
        mapTripMode = (RadioGroup) findViewById(R.id.mapTripMode);
        mapTripMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.mapTripModeTaxi) {
                    // 驾车
                    mapDistance.setText(mapInfos[0][0]);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.mapTripModeBus) {
                    // 公交
                    mapDistance.setText(mapInfos[1][0]);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.mapTripModeWalk) {
                    // 步行
                    mapDistance.setText(mapInfos[2][0]);
                }
            }
        });
        mapTripModeTaxi = (RadioButton) findViewById(R.id.mapTripModeTaxi);
        mapTripModeBus = (RadioButton) findViewById(R.id.mapTripModeBus);
        mapTripModeWalk = (RadioButton) findViewById(R.id.mapTripModeWalk);
        mapDestinationAddress = (TextView) findViewById(R.id.mapDestinationAddress);
        mapDistance = (TextView) findViewById(R.id.mapDistance);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        dialog = new LoadingDialog(this);
        dialog.show();
        dialog.setLoadingTitle("正在规划……");
    }

    private void initMap() {
        // 目标
        mapDestinationAddress.setText(getIntent().getStringExtra("address"));
        double longitude = getIntent().getDoubleExtra("longitude", 39.906901); // 默认北京天安门坐标
        double latitude = getIntent().getDoubleExtra("latitude", 116.397972);
        latLng = new LatLng(longitude, latitude);
        mEndPoint = new LatLonPoint(longitude, latitude);
        aMap = mMapView.getMap();
        cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 16, 0, 30));
        aMap.moveCamera(cameraUpdate);
        Marker marker = aMap.addMarker(new MarkerOptions().position(latLng));
        marker.showInfoWindow();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //该方法默认为false，true表示只定位一次
        mLocationOption.setOnceLocation(true);
        // 路径规划监听
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(onRouteSearchListener);
    }

    /**
     * 路径规划
     */
    private void pathPlanning() {
        if (isFirst) {
            isFirst = false;
            // 设置起始点
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
            // 驾车
            RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
            routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
            // 公交
            RouteSearch.BusRouteQuery busRouteQuery = new RouteSearch.BusRouteQuery(fromAndTo, RouteSearch.BusDefault, city, 0);
            routeSearch.calculateBusRouteAsyn(busRouteQuery);
            // 步行
            RouteSearch.WalkRouteQuery walkRouteQuery = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
            routeSearch.calculateWalkRouteAsyn(walkRouteQuery);
        }
    }

    /**
     * mapInfos 是否通过（都不为空时通过）
     */
    private boolean mapInfosIsAdopt() {
        for (String[] items : mapInfos) {
            for (String item : items) {
                if (GeneralUtils.isEmpty(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        mLocationClient.stopLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 到这里去点击事件
     *
     * @param view
     */
    public void goHere(View view) {
        try {
            Uri uri = Uri.parse("geo:" + latLng.longitude + "," + latLng.latitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            DialogUtil.prompt(this, "很抱歉，没有找到导航工具！", "知道了", null);
        }
    }
}
